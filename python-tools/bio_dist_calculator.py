from EdgeNumberTool import EdgeNumberTool
from GRNCSVReader import GRNCSVReader
from scipy.stats.stats import pearsonr
import math
import scipy.stats as ss


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


bio_dist_10 = get_bio_dist_list(10, 0.15)
bio_dist_5 = get_bio_dist_list(5, 0.15)

print('bio dist: ', bio_dist_5)

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

print('a sum: ', a_sum)
print('sums: ', sums)

print(avg_gamma_formula(a_sum))



