import random


def main():
    randomowe = []

    for i in range(600000):
        randomowe.append(i)
    #print(randomowe)
    rand_lst = [[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[]]
    for j in range(30):
        print(j)
        for i in range(500+500*j):
            k = random.randrange(100000)
            #rand_lst[j].append(str(random.randrange(10000)))
            rand_lst[j].append(str(randomowe[k]))
            randomowe.pop(k)
    print(rand_lst)
    text_file = open("data.txt", "w")
    for j in range(50):
        text_file.write(" ".join(rand_lst[j]))
        text_file.write(" ")
if __name__ == "__main__":
    main()
