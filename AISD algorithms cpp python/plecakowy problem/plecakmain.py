import time
import random
import sys
sys.setrecursionlimit(1500000000)
import pprint


def main():

    plecakiOstat = []
    num_of_test = 25
    num_of_items = 5
    range_of_random = 15

    for i in range(num_of_test):

        plecaki = []

        for j in range(num_of_items):
            a = random.randint(1, range_of_random)
            b = random.randint(1, range_of_random)
            plecaki.append([a, b])

        plecakiOstat.append(plecaki)
        num_of_items += 1

    print(plecakiOstat)
    text_file = open("plecaki2.txt", "w")
    text_file.write(str(plecakiOstat))


if __name__ == "__main__":
    main()