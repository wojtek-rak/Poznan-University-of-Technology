import random
import numpy as np

np.set_printoptions(edgeitems=100000)
np.core.arrayprint._line_width = 1800000



def calc(mc, quan):
    sum = 0
    for i in range(quan):
        for j in range(quan):
            if mc[i][j] != -1:
                sum += 1
    return sum

def main():
    randomowe = []

    lst = []

    sizeof = 5
    numOfMatrix = 15

    size = sizeof
    it = 1
    nas = 0.5
    dodaw = False
    break_point = False
    for i in range(numOfMatrix):
        x = np.zeros((size, size)).astype(int)
        for ke in range(len(x[0])):
            for re in range(len(x[0])):
                x[ke][re] = -1
        while (it < size):
            for j in range(size - it):
                if calc(x, size) / (size * size) > nas:
                    break_point = True
                    break
                x[j][j + it] = j + it
                x[j + it][j] = j
            if (break_point):
                break
            it += 1
        sum = 0
        break_sum = False
        break_if = True
        while(break_if):
            for j in range(size):
                for k in range(size):
                    if (x[j][k] != -1):
                        sum += 1
                print(sum)
                if (sum % 2 == 1):
                    for k in range(size):
                        kap = random.randint(1, size - 1)
                        if (dodaw):
                            if (x[j][kap] != -1):
                                x[j][kap] = -1
                                x[kap][j] = -1
                                dodaw = False
                                break
                        else:
                            if (x[j][kap] == -1 and j != kap):
                                x[j][kap] = kap
                                x[kap][j] = j
                                dodaw = True
                                break
                sum = 0
            break_sum = False
            for j in range(size):
                for k in range(size):
                    if (x[j][k] != -1):
                        sum += 1
                if ( sum % 2 == 1):
                    break_sum = True
                sum = 0
            if(break_sum == False):
                break_if = False

        size += 1
        it = 1
        break_point = False
        print (x)
        x = np.array2string(x, max_line_width=None)
        lst.append(x)
    print(lst)
    x = np.zeros((3,3)).astype(int)

    print(type(x))
    print(x)

    text_file = open("data_macierz_hamilton.txt", "w")
    for j in range(numOfMatrix):
        text_file.write("".join(lst[j]))
        text_file.write("\nnext\n")

if __name__ == "__main__":
    main()
