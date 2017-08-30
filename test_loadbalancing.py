#!/usr/bin/python3

import requests
import threading


def get_url(url):
    requests.get(url).text



def start_spamming():
    for j in range(5):
        for i in range(20):
            t = threading.Thread(target=get_url, args=("http://localhost:8080/catalog/items",))
            t.start()

    print("Done.")


if __name__ == '__main__':
    start_spamming()
