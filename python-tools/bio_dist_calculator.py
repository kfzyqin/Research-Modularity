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


bio_dist_10 = get_bio_dist_list(10, 0.15)
bio_dist_5 = get_bio_dist_list(5, 0.15)








