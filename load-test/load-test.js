import http from "k6/http";
import { sleep } from "k6";

export const options = {
    vus: 500,
    duration: "30s",
};

export function setup() {

    for (let i = 0; i < 2000; i++) {
        http.post("http://localhost:8080/api/todos",
            JSON.stringify({ title: "seed todo" }),
            { headers: { "Content-Type": "application/json" } }
        );
    }
}

export default function () {
    http.get("http://localhost:8080/api/todos");
    sleep(1);
}