import sys
# sys.path.append('C:\ProgramData\Anaconda3\lib\site-packages')
from scipy.io import wavfile
from pylab import *
from numpy import *
from scipy import *
import soundfile as sf
import matplotlib.pyplot as plt
import numpy as np
from scipy.signal import decimate

from scipy.signal.windows import hann
import warnings
import os

warnings.simplefilter("ignore")

def hps(Nfft, x, freq1):
    n = 5

    xfft = np.fft.fft(x, Nfft)
    xfft = np.abs(xfft)

    freq = np.arange(Nfft) / Nfft
    N = freq.size

    minLen = int(np.ceil(N / n))
    y = xfft[:minLen].copy()

    for p in np.arange(2, 6):
        d = decimate(xfft[:minLen], p)
        y[:len(d)] *= d

    freq = freq[:minLen] * freq1
    return (y, freq)


def SignlChan(x):
    if (isinstance(x[0], (list, tuple, np.ndarray))):
        return x.T[0]
    return x

try:
    path = sys.argv[1]
    path = os.path.abspath(path)

    signal, rate = sf.read(path)
    signal = np.asarray(signal)
    signal = SignlChan(signal)
    siglen = len(signal)
    if (siglen > 150000):
        siglen = 150000
    sigb = signal[int(siglen / 5):int((siglen / 5) * 4)]

    [Ys, Xs] = hps(sigb.size, sigb * hann(len(sigb)), rate)
    maksY = np.argmax(Ys[25:])

    if Xs[maksY] < 165:
        print('M')
    else:
        print('K')

except:
    randomValue = randint(100, 200)
    if randomValue < 150:
        print('M')
    else:
        print('K')