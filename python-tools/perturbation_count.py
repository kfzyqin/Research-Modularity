#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Sun Aug  2 17:32:58 2020

@author: rouyijin
"""

import os
import re
import numpy as np
import matplotlib.pyplot as plt

file1 = "/Users/rouyijin/Desktop/IEEE-Alife-Results/Rouyi-selection-k-0.5-2000-max-2-with-perturbationMap-random-init"
file2 = "/Users/rouyijin/Desktop/IEEE-Alife-Results/Zhenyue\'s-0.5-2000/mutation-0.2-x-0.2"
file3 = "/Users/rouyijin/Desktop/IEEE-Alife-Results/Zhenyue\'s-500-2000/mutation-0.2-x-0.2"
file4 = '/Users/rouyijin/Desktop/IEEE-Alife-Results/Rouyi-selection-500-2000-perturbationMap-random-init'

sample_size = 100
min_gen = 0
max_gen = 1000


def read_avg_perturbations(path, sample_size):
    fileName = 'perturbationNum.maps'
    entries = os.listdir(path)
    all_sample_maps = []
    perturbation_numbers = []

    for entry in entries:
        if entry[0] != '.':
            map_path = path + '/' + entry + '/' + fileName
            file = open(map_path, "r")
            list_of_lines = file.readlines()
            all_sample_maps.append(list_of_lines)

    for trial in all_sample_maps[:sample_size]:
        trial = [re.sub(r'^.*?=', '', stri) for stri in trial]
        trial = list(map(int, trial))
        perturbation_numbers.append(trial)

    #    calculate average
    avg_perturbations = np.mean(perturbation_numbers, 0)
    return avg_perturbations


def perturbation_range(min_gen, max_gen, lst):
    return lst[min_gen: max_gen]


perturbation_average_2000_gen = read_avg_perturbations(file4, sample_size)
perturbation_average_500_1000_gen = perturbation_range(min_gen, max_gen, perturbation_average_2000_gen)

# drawing
# x = list(range(0, 1000))
## multiple line plot
# plt.plot(x, perturbation_average_500_1000_gen, marker='x', label = "progressive",
#         color='red', linewidth=1, markevery=100, markersize = 8)
# plt.hlines(307200, 0, 500, label = "progressive", color='green', linestyles='solid')
# plt.hlines(614400, 500, 1000, color='green', linestyles='solid')
#
#
##plt.plot(x, fitness_k, marker='^', label = "fitness with k and tournament selection",
##         color='green', linewidth=1, markevery=20, markersize = 8)
# plt.xlabel('generation', fontsize=14)
# plt.ylabel('perturbations', fontsize=14)
# plt.xticks(fontsize=13)
# plt.yticks(fontsize=13)
##plt.ylim(0.55, 0.95)
# plt.legend(loc='center right', fontsize=13)
# plt.tight_layout()
##plt.savefig('fitness_no_k_k.png', dpi=500)


f, (ax, ax2) = plt.subplots(2, 1, sharex=False)
# plot the same data on both axes
ax.hlines(307200, 0, 500, color='green', linestyles='solid')
ax.hlines(614400, 500, 1000, color='green', linestyles='solid')

ax.hlines(150000, 0, 500, color='blue', linestyles='solid')
ax.hlines(300000, 500, 1000, color='blue', linestyles='solid')

ax2.plot(perturbation_average_500_1000_gen, label="progressive", color='red')
ax2.plot(0, label="distributional", color='green')
ax2.plot(0, label="stochastic", color='blue')
ax.xaxis.set_major_locator(plt.NullLocator())

# ax.legend(loc='lower right', fontsize=13) # 让图例生效
plt.legend(loc='upper right', fontsize=13)
ax.tick_params(labelsize=13)
plt.tick_params(labelsize=13)

plt.xlabel('generation', fontsize=14)
plt.ylabel('perturbations', fontsize=14)

ax2.set_ylim(0, 4000)
# ax.set_ylim(280000, 650000)
ax.set_ylim(120000, 650000, 100000)

# hide the spines between ax and ax2
ax.spines['bottom'].set_visible(False)
ax2.spines['top'].set_visible(False)

d = .015  # how big to make the diagonal lines in axes coordinates
# arguments to pass to plot, just so we don't keep repeating them
kwargs = dict(transform=ax.transAxes, color='k', clip_on=False)
ax.plot((-d, +d), (-d, +d), **kwargs)  # top-left diagonal
ax.plot((1 - d, 1 + d), (-d, +d), **kwargs)  # top-right diagonal

kwargs.update(transform=ax2.transAxes)  # switch to the bottom axes
ax2.plot((-d, +d), (1 - d, 1 + d), **kwargs)  # bottom-left diagonal
ax2.plot((1 - d, 1 + d), (1 - d, 1 + d), **kwargs)  # bottom-right diagonal

plt.tight_layout()
plt.savefig('/Users/rouyijin/Desktop/perturbation_prog_vs_distributional.png', dpi=500, bbox_inches='tight')
plt.show()