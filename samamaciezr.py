import random
import numpy as np

np.set_printoptions(edgeitems=100000)
np.core.arrayprint._line_width = 1800000



def calc(mc, quan):
    sum = 0
    for i in range(quan):
        for j in range(quan):
            if mc[i][j] != 0:
                sum += 1
    return sum

def main():
    randomowe = []

    lst = []

    sizeof = 10

    size = sizeof
    it = 1
    nas = 0.7
    break_point = False
    for kp in range(2):
        for i in range(5):
            x = np.zeros((size, size)).astype(int)
            while (it < size):
                for j in range(size - it):
                    if calc(x, size) / (size * size) > nas:
                        break_point = True
                        break
                    ran = 1# random.randint(1, 1000)
                    x[j][j + it] = ran
                    x[j + it][j] = ran
                if (break_point):
                    break
                it += 1

            size += sizeof
            it = 1
            break_point = False
            x = np.array2string(x,max_line_width=None)
            lst.append(x)

        nas = 0.3
        size = sizeof
        it = 1
    print(lst)
    x = np.zeros((3,3)).astype(int)

    print(type(x))
    print(x)

    text_file = open("data_macierz.txt", "w")
    for j in range(10):
        text_file.write("".join(lst[j]))
        text_file.write("\nnext\n")

if __name__ == "__main__":
    main()
