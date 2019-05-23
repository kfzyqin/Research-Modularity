from EdgeNumberTool import EdgeNumberTool
from GRNCSVReader import GRNCSVReader
from scipy.stats.stats import pearsonr
import math
import scipy.stats as ss
import os
import operator as op
import numpy as np


def ncr(n, r):
    r = min(r, n-r)
    numer = reduce(op.mul, range(n, n-r, -1), 1)
    denom = reduce(op.mul, range(1, r+1), 1)
    return numer / denom


def get_bio_dist_list(n, p):
    bio_dist = []

    for i in range(0, n+1):
        bio_dist.append(ss.binom.pmf(i, n, p))

    return bio_dist


def get_dst_gamma(d, d_max):
    gamma = math.pow((1.0 - (d/float(d_max))), 5)
    return gamma


def avg_gamma_formula(a_gamma):
    return 1 - math.pow(math.e, (-3 * a_gamma))


if __name__ == "__main__":
    bio_dist_10 = get_bio_dist_list(10, 0.15)
    bio_dist_5 = get_bio_dist_list(5, 0.15)

    print('bio dist 10: ', bio_dist_10)
    print('bio dist 5: ', bio_dist_5)

    an_n = 5
    max_gamma = get_dst_gamma(0, an_n)
    a_sum = 0

    sums = []
    for i in range(0, an_n+1):
        if i < (an_n / 2.0):
            sums.append(bio_dist_5[i] * get_dst_gamma(0, 5))
            a_sum += bio_dist_5[i] * get_dst_gamma(0, 5)
        else:
            sums.append(bio_dist_5[i] * get_dst_gamma(5, 5))
            a_sum += bio_dist_5[i] * get_dst_gamma(5, 5)
            # sums.append(0)
            # a_sum += 0

    b_sum = []
    fits_10 = []
    for i in range(0, 11):
        numerator_sum = 0
        denominator = ncr(10, i)
        if i >= 3 and i <= 7:
            print('i: ', i)
            if i <= 5:
                for j in range(3, i+1):
                    numerator_sum += ncr(5, j) * ncr(5, (i-j))
            else:
                for j in range(3, 5+1):
                    numerator_sum += ncr(5, j) * ncr(5, (i-j))
            lost_prob = float(numerator_sum) / float(denominator)
            lost_fit = get_dst_gamma(5, 10) * bio_dist_10[i] * lost_prob
            print('lost fit: ', lost_fit)
            fits_10.append(bio_dist_10[i] * get_dst_gamma(0, 10) * (1-lost_prob) + bio_dist_10[i] * get_dst_gamma(5, 10) * lost_prob)
        elif i > 7:
            lost_fit = get_dst_gamma(5, 10) * bio_dist_10[i]
            print('lost fit: ', lost_fit)
            fits_10.append(bio_dist_10[i] * get_dst_gamma(5, 10))
        else:
            fits_10.append(bio_dist_10[i] * get_dst_gamma(0, 10))

    print('a sum: ', a_sum)
    print('sums: ', sums)

    print(avg_gamma_formula(a_sum))

    print('fits 10: ', fits_10)
    print(avg_gamma_formula(np.sum(fits_10)))

    print('gamma 0: ', get_dst_gamma(0, 10))
    print('gamma 5: ', get_dst_gamma(5, 10))

