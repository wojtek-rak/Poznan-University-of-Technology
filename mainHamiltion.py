import time
import random
import sys
sys.setrecursionlimit(1500000000)
import pprint


def find_all_paths(graph, start, end, path=[]):
    path = path + [start]
    if start == end:
        return [path]
    paths = []
    for node in graph[start]:
        if node not in path:
            newpaths = find_all_paths(graph, node, end, path)
            for newpath in newpaths:
                paths.append(newpath)
    return paths

"""
def find_all_paths(graph, start, end, path=[]):
    path = path + [start]
    if start == end:
        return [path]
    if not graph.has_key(start):
        return []
    paths = []
    for node in graph[start]:
        if node not in path:
            newpaths = find_all_paths(graph, node, end, path)
            for newpath in newpaths:
                paths.append(newpath)
    return paths """

kp = 1

def zapis(wyn):
    global  kp
    print(kp)
    print(wyn)
    print("done")
    print()
    kp += 1

    text_file = open("hamilton_wyn3.txt", "w")
    text_file.write("\n".join(wyn))
    text_file.close()

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
        plik = open('data_macierz_hamilton.txt')
        lst = plik.read()
        lst = lst.replace("]]", "]")
        lst = lst.replace("[", "")
        lst = lst.replace("\n", "")
        lst = lst.split("next")
    finally:
        plik.close()

    wyniki = []
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
    #print(mac_list)
    #for i in mac_list:
    #    for j in i:
    #        print(j)
    wyn = []
    lst_sums = []
    sum_ham = 0
    for mac_ham in mac_list:

        keys = list(range(0, len(mac_ham[0])))
        #print(keys)
        mac = dict(zip(keys, zip(*zip(*mac_ham))))
        #print(mac)
        for dic in keys:
            mac[dic] = list(mac[dic])
        #print(mac)
        for dic in keys:
            mac[dic] = [x for x in mac[dic] if x != -1]
            # try:
            #    b = mac[dic].index(-1)
            #    del mac[dic][b]
            # except:
            #    pass
            # print(type(mac[dic]))
        #print(mac)
        start = time.time()
        for st in mac[0]:
            lst = find_all_paths(mac, st, 0)
            for k in lst:
                if (len(k) == len(keys)):
                    sum_ham += 1
        end = time.time() - start
        wyn.append(str(end))
        #for ke in keys:
        #    lst = find_all_paths(mac, 0, ke)
        #    for k in lst:
        #        if (len(k) == len(keys) and 0 in mac[k[len(keys)-1]]):
        #            sum_ham += 1
        lst_sums.append(sum_ham)
        sum_ham = 0
        #[0, 4, 16, 8, 20, 482]

        print(len(mac_ham))
        print(time.time() - start)
        zapis(wyn)
        if len(mac_ham) == 15:
            break



if __name__ == "__main__":
    main()
