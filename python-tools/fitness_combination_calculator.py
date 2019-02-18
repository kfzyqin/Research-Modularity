import operator as op
from functools import reduce
import math
import scipy.stats as ss


def get_dst_gamma(d, d_max):
    gamma = math.pow((1.0 - (d/float(d_max))), 5)
    return gamma


def avg_gamma_formula(a_gamma):
    return 1 - math.pow(math.e, (-3 * a_gamma))


def ncr(n, r):
    assert r <= n
    r = min(r, n-r)
    numer = reduce(op.mul, range(n, n-r, -1), 1)
    denom = reduce(op.mul, range(1, r+1), 1)
    return numer / denom


def get_bio_dist_list(n, p):
    bio_dist = []

    for i in range(0, n+1):
        bio_dist.append(ss.binom.pmf(i, n, p))

    return bio_dist


gamma_5 = get_dst_gamma(5, 10)
gamma_0 = get_dst_gamma(0, 10)
bio_dist_10 = get_bio_dist_list(10, 0.15)

probs = []
gammas = []

for n in range(0, 2+1):
    gammas.append(get_dst_gamma(0, 10))

for n in range(3, 7+1):
    a_prob = 0
    for i in range(3, n+1 if n <= 5 else (5+1)):
        a_numerator = (ncr(5, i)) * (ncr(5, (n-i)))
        a_denominator = (ncr(10, n))
        print('n: ', n, '; a numerator: ', a_numerator, '; a denominator: ', a_denominator)
        a_prob += a_numerator / float(a_denominator)

        if n == 7:
            print(a_numerator, "  ;", a_denominator)

    probs.append(a_prob)
    gammas.append((a_prob * gamma_5) + ((1-a_prob) * gamma_0))

for n in range(8, 10+1):
    gammas.append(gamma_5)

print(gammas)

weighted_sum = 0
for i in range(0, 11):
    weighted_sum += bio_dist_10[i] * gammas[i]
print('weighted sum: ', weighted_sum)

print(avg_gamma_formula(weighted_sum))

print( ((11/12.0) * gamma_5) + ((1/12.0) * gamma_0) )