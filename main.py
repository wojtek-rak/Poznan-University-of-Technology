import time
import random
import sys
sys.setrecursionlimit(1500000000)

def partition(tab, st, en):
    #x = tab[int((st + en) // 2)]
    #x = tab[en-1]
    x = tab[random.randrange(en -1)]
    i = st - 1
    j = en + 1
    while (True):
        while(True):
            j -= 1
            if tab[j] <= x:
                break
        while(True):
            i += 1
            if tab[i] >= x:
                break
        if i < j:
            tab[i], tab[j] = tab[j], tab[i]
        else:
            return j



def quicksort(tab, p, e):

    if p < e:
        q = partition(tab,p,e)
        quicksort(tab,p,q)
        quicksort(tab, q + 1, e)
def quicksort_setup(tab):
    quicksort(tab,0,len(tab) - 1)
    return tab


def main():
    #try:
    #    plik = open('big.in')
    #    tekst = plik.read()
    #    tekst = re.split(r'[ \n]', tekst)
    #finally:
    #    plik.close()
    tab = [100,34,1233,4,39213,1,2334,324,31244,23,423415,0]
    tab = [5, 20, 20, 24, 63, 51, 6]
    try:
        plik = open('data_macierz.txt')
        lst = plik.read()
        lst = lst.replace("]]", "]")
        lst = lst.replace("[", "")
        lst = lst.replace("\n", "")
        lst = lst.split("next")
    finally:
        plik.close()

    mac_list = []
    temp_list = []
    for i in lst:
        temp = i.split("]")
        for j in temp:
            temp2 = j.split(" ")
            temp2 = list(filter(None, temp2))
            temp2 = list(map(int, temp2))
            temp_list.append(temp2)
        temp_list = list(filter(None, temp_list))
        mac_list.append(temp_list)
        temp_list = []
    print(mac_list)
    for i in mac_list:
        for j in i:
            print(j)
    lst_results_all = []
    lst_results = []
    it_x = 0
    it_y = 0
    sum = 1001

    counter = 0

    it_x_temp = 0
    it_y_temp = 0

    for te in mac_list:
        it_x = 0
        it_y = 0
        sum = 1001

        it_x_temp = 0
        it_y_temp = 0

        lst_results.append(0)
        while(True):
            for j in range(0,len(te[0])):#it_y, len(te[0]), 1):
                if (te[it_x][j] < sum and te[it_x][j] != 0):
                    sum = te[it_x][j]
                    it_x_temp = j * 1
                    it_y_temp = it_y * 1
            it_y = it_x * 1
            it_x = it_x_temp * 1
            lst_results.append(it_x_temp)
            sum = 1001
            for j in range(0, len(te[0])):
                te[it_y][j] = 0
            for j in range(0, len(te[0])):
                te[j][it_y] = 0
            #print(len(lst_results))
            if len(lst_results) >= len(te[0]):
                counter += 1
                lst_results_all.append(lst_results)
                lst_results = []
                print("done")
                break
        if counter == 12:
            break
    print(lst_results_all)



# ?>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    try:
        plik = open('data_macierz.txt')
        lst = plik.read()
        lst = lst.replace("]]", "]")
        lst = lst.replace("[", "")
        lst = lst.replace("\n", "")
        lst = lst.split("next")
    finally:
        plik.close()

    mac_list = []
    temp_list = []
    for i in lst:
        temp = i.split("]")
        for j in temp:
            temp2 = j.split(" ")
            temp2 = list(filter(None, temp2))
            temp2 = list(map(int, temp2))
            temp_list.append(temp2)
        temp_list = list(filter(None, temp_list))
        mac_list.append(temp_list)
        temp_list = []
    print(mac_list)
    #for i in mac_list:
    #    for j in i:
    #        print(j)


    keys = list(range(0,len(mac_list[0][0])))
    print(keys)
    mac = dict(zip(keys, zip(*mac_list[0])))
    print(mac)





    #print(lst[0])

    #for j in temp:
    #    print(j)
    #temp2 = temp[0].split(" ")
    #temp2 = list(filter(None, temp2))
    #print(temp2)


    #dane = []
    #for i in lst:
    #    dane.append(int(i))

    """
    
    for j in range(50):
        lst[j] = lst[j].split(" ")

    for j in range(50):
        print(len(lst[j]))
        print()
        for i in range(len(lst[j])):

            lst[j][i] = int(lst[j][i])

    #print(lst)

    wyniki = []
    for i in lst:
        arrt = i * 1
        start = time.time()
        #print(time.time())
        quicksort_setup(arrt)
        end = time.time()
        #print(time.time())
        wyn1 = end - start

        arrt = i * 1
        start = time.time()
        # print(time.time())
        quicksort_setup(arrt)
        end = time.time()
        # print(time.time())
        wyn2 = end - start

        arrt = i * 1
        start = time.time()
        # print(time.time())
        quicksort_setup(arrt)
        end = time.time()
        # print(time.time())
        wyn3 = end - start

        arrt = i * 1
        start = time.time()
        # print(time.time())
        quicksort_setup(arrt)
        end = time.time()
        # print(time.time())
        wyn4 = end - start

        arrt = i * 1
        print(arrt)
        start = time.time()
        # print(time.time())
        quicksort_setup(arrt)
        end = time.time()
        # print(time.time())
        wyn5 = end - start

        arrt = i * 1
        start = time.time()
        # print(time.time())
        quicksort_setup(arrt)
        end = time.time()
        # print(time.time())
        wyn6 = end - start

        arrt = i * 1
        start = time.time()
        # print(time.time())
        quicksort_setup(arrt)
        end = time.time()
        # print(time.time())
        wyn7 = end - start

        arrt = i * 1
        start = time.time()
        # print(time.time())
        quicksort_setup(arrt)
        end = time.time()
        # print(time.time())
        wyn8 = end - start

        arrt = i * 1
        start = time.time()
        # print(time.time())
        quicksort_setup(arrt)
        end = time.time()
        # print(time.time())
        wyn9 = end - start

        arrt = i * 1
        start = time.time()
        # print(time.time())
        quicksort_setup(arrt)
        end = time.time()
        # print(time.time())
        wyn10 = end - start

        arrt = i * 1
        start = time.time()
        # print(time.time())
        quicksort_setup(arrt)
        end = time.time()
        # print(time.time())
        wyn11 = end - start

        arrt = i * 1
        start = time.time()
        # print(time.time())
        quicksort_setup(arrt)
        end = time.time()
        # print(time.time())
        wyn12 = end - start

        arrt = i * 1
        start = time.time()
        # print(time.time())
        quicksort_setup(arrt)
        end = time.time()
        # print(time.time())
        wyn13 = end - start

        arrt = i * 1
        start = time.time()
        # print(time.time())
        quicksort_setup(arrt)
        end = time.time()
        # print(time.time())
        wyn14 = end - start

        arrt = i * 1
        start = time.time()
        # print(time.time())
        quicksort_setup(arrt)
        end = time.time()
        # print(time.time())
        wyn15 = end - start

        print(wyn1, " ",wyn2, " ",wyn3, " ",wyn4, " ",wyn5, " ")
        print()
        wyn = (wyn1 + wyn2 + wyn3 + wyn4 + wyn5 + wyn6 + wyn7 + wyn8 + wyn9 + wyn10 + wyn11 + wyn12 + wyn13 + wyn14 + wyn15)/15
        #wyn = (wyn1 + wyn2 + wyn3 + wyn4 + wyn5 + wyn6 + wyn7 + wyn8 + wyn9 + wyn10 + wyn11 + wyn12 + wyn13 + wyn14 + wyn15)/15
        wyniki.append(str(wyn))
    print(wyniki)
    for i in wyniki:
        print(i)


    text_file = open("xD_wyniki_A_rand.txt", "w")
    text_file.write("\n".join(wyniki))
"""
if __name__ == "__main__":
    main()
