package main

import (
	"context"
	"database/sql"
	"fmt"
	"io"
	"net/http"
	"net/url"
	"os"
	"os/signal"
	"syscall"

	"github.com/google/uuid"
	_ "github.com/lib/pq"
	_ "github.com/mattn/go-sqlite3"
	"github.com/mdp/qrterminal/v3"
	"github.com/zeebo/xxh3"
	"go.mau.fi/whatsmeow"
	waProto "go.mau.fi/whatsmeow/binary/proto"
	"go.mau.fi/whatsmeow/store/sqlstore"
	"go.mau.fi/whatsmeow/types/events"
	waLog "go.mau.fi/whatsmeow/util/log"
	"google.golang.org/protobuf/proto"
)

var client *whatsmeow.Client
var juntemonosAiServiceURL = os.Getenv("JUNTEMONOS_AI_SERVICE_URL")
var logLevel = "INFO"
var messageLog = waLog.Stdout("message_event", logLevel, true)

func eventHandler(evt interface{}) {

	switch v := evt.(type) {
	case *events.Message:
		messageLog.Infof("Received a message!", v.Message.GetConversation())
		if v.Message.GetConversation() != "" {
			base, err := url.Parse(juntemonosAiServiceURL)
			if err != nil {
				panic(err)
			}
			h := xxh3.HashString128(v.Info.MessageSource.Sender.String()).Bytes()
			guid, _ := uuid.FromBytes(h[:])
			// Query params
			params := url.Values{}
			params.Add("message", v.Message.GetConversation())
			params.Add("user", guid.String())
			base.RawQuery = params.Encode()
			messageLog.Infof("Encoded URL is %q\n", base.String())
			resp, err := http.Get(base.String())
			if err != nil {
				panic(err)
			}
			defer resp.Body.Close()
			if resp.StatusCode == http.StatusOK {
				bodyBytes, err := io.ReadAll(resp.Body)
				if err != nil {
					panic(err)
				}
				bodyString := string(bodyBytes)

				msg := &waProto.Message{Conversation: proto.String(bodyString)}
				r, err := client.SendMessage(context.Background(), v.Info.MessageSource.Sender.ToNonAD(), msg)
				if err != nil {
					panic(err)
				} else {
					messageLog.Infof("Message sent (server timestamp: %s)", r.Timestamp)
				}
			}

		}

	}
}

func main() {
	dbLog := waLog.Stdout("Database", logLevel, true)
	psqlInfo := fmt.Sprintf("host=%s port=%s user=%s "+
		"password=%s dbname=%s sslmode=disable",
		os.Getenv("POSTGRES_HOST"), os.Getenv("POSTGRES_PORT"), os.Getenv("POSTGRES_USER"), os.Getenv("POSTGRES_PASSWORD"), os.Getenv("POSTGRES_DB"))
	db, err := sql.Open("postgres", psqlInfo)
	if err != nil {
		panic(err)
	}
	defer db.Close()
	err = db.Ping()
	if err != nil {
		panic(err)
	}
	// Make sure you add appropriate DB connector imports, e.g. github.com/mattn/go-sqlite3 for SQLite
	container := sqlstore.NewWithDB(db, "postgres", dbLog)
	err = container.Upgrade()
	if err != nil {
		panic(err)
	}
	// If you want multiple sessions, remember their JIDs and use .GetDevice(jid) or .GetAllDevices() instead.
	deviceStore, err := container.GetFirstDevice()
	if err != nil {
		panic(err)
	}
	clientLog := waLog.Stdout("Client", "DEBUG", true)
	client = whatsmeow.NewClient(deviceStore, clientLog)
	client.AddEventHandler(eventHandler)

	if client.Store.ID == nil {
		// No ID stored, new login
		qrChan, _ := client.GetQRChannel(context.Background())
		err = client.Connect()
		if err != nil {
			panic(err)
		}
		for evt := range qrChan {
			if evt.Event == "code" {
				// Render the QR code here
				// e.g.
				qrterminal.GenerateHalfBlock(evt.Code, qrterminal.L, os.Stdout)
				// or just manually `echo 2@... | qrencode -t ansiutf8` in a terminal
				fmt.Println("QR code:", evt.Code)
			} else {
				fmt.Println("Login event:", evt.Event)
			}
		}
	} else {
		// Already logged in, just connect
		err = client.Connect()
		if err != nil {
			panic(err)
		}
	}

	// Listen to Ctrl+C (you can also do something else that prevents the program from exiting)
	c := make(chan os.Signal, 1)
	signal.Notify(c, os.Interrupt, syscall.SIGTERM)
	<-c

	client.Disconnect()
}
