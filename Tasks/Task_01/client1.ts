import * as dotenv from "dotenv";
dotenv.config({ path: __dirname + "/.env" });

import * as mqtt from "mqtt";
import { MqttClient } from "mqtt/types/lib/client";

console.log(" --- Starting Client 1 --- ");

let client: MqttClient = mqtt.connect(process.env.MQTT_BROKER_URL ?? "");

client.publish("hallowelt", "Hello mqtt");

client.end();

console.log(" --- Terminating Client 1 --- ");
