from CSVFileOpener import CSVFileOpener
import file_processor as fp
import numpy as np
import tools.storage.fitness_warehouse as fitness_warehouse
from StatisticsToolkit import StatisticsToolkit
import os
import pandas as pd
import matplotlib.pyplot as plt

# file = "/Users/rouyijin/Desktop/csvfiles"
csv_opener = CSVFileOpener()

# attribute is fitness or modularity name in csv
def best_avg(filePath, attribute, min_gen, max_gen, sample_size):
    entries = os.listdir(filePath)
    best_lst = []
    avg_lst = [0] * (max_gen - min_gen)

    for entry in entries:
        if entry[0] != '.':
            path = filePath + '/' + entry
            best_lst.append(csv_opener.get_column_values_of_an_trial(path, attribute))
    # sample size
    best_lst = best_lst[:sample_size]

    # generation range
    for element in best_lst:
        #        range_lst.append(element[min_gen : max_gen])
        avg_lst = [a + b for a, b in zip(avg_lst, element[min_gen: max_gen])]

    avg_lst = [x / sample_size for x in avg_lst]
    return avg_lst




def statistics_read_sample_size(filePath, attribute, sample_size):
    entries = os.listdir(filePath)
    best_lst = []
    for entry in entries:
        if entry[0] != '.':
            path = filePath + '/' + entry
            best_lst.append(csv_opener.get_column_values_of_an_trial(path, attribute))
    # sample size
    best_lst = best_lst[:sample_size]
    return best_lst


def statistics_avg_generation_range(min_gen, max_gen, lst, sample_size):
    avg_lst = [0] * (max_gen - min_gen)
    for element in lst:
        avg_lst = [a + b for a, b in zip(avg_lst, element[min_gen: max_gen])]

    avg_lst = [x / sample_size for x in avg_lst]
    return avg_lst


def best_mod_avg(path, min_gen, max_gen, sample_size):
    mods, stdevs = csv_opener.get_mod_of_each_generation_in_a_whole_exp_with_stdev(path, modularity_key, 100)
    modularity = mods[min_gen: max_gen]
    return modularity


sample_size = 100
min_gen = 1
max_gen = 1001
fitness_key = 'Best'
modularity_key = 'FittestModularity'

file1 = "/Users/rouyijin/Desktop/IEEE-Alife-Results/Zhenyue\'s-500-2000/mutation-0.2-x-0.2"
file2 = "/Users/rouyijin/Desktop/IEEE-Alife-Results/Rouyi-selection-k-0.5-2000-max-2-with-perturbationMap-random-init"
file3 = "/Users/rouyijin/Desktop/IEEE-Alife-Results/Zhenyue\'s-0.5-2000/mutation-0.2-x-0.2"
file4 = '/Users/rouyijin/Desktop/IEEE-Alife-Results/Rouyi-selection-500-2000-perturbationMap-random-init'
file5 = "/Users/rouyijin/Desktop/IEEE-Alife-Results/stoc-x-0.2-0.2"
tecDis = "/Users/rouyijin/Desktop/IEEE-Alife-Results/ZQTEC/2020-distributional-x-p00"
tecSto = "/Users/rouyijin/Desktop/IEEE-Alife-Results/ZQTEC/2020-stochastic-x-p00"
# original stochastic
stoc_diag_f = "/Users/rouyijin/Desktop/IEEE-Alife-Results/stoc-x-0.2-0.2"
stoc_hori_f = "/Users/rouyijin/Desktop/IEEE-Alife-Results/stoc-x-0.2-0.2-horizontal-x"
# dynamic stochastic
dyn_sto_f = "/Users/rouyijin/Desktop/IEEE-Alife-Results/Stochastic-0.5-2000-random-init"

# # fitness
# original = statistics_read_sample_size(file1, fitness_key, sample_size)
# original_avg = statistics_avg_generation_range(min_gen, max_gen, original, sample_size)
#
# dynamic = statistics_read_sample_size(file3, fitness_key, sample_size)
# dynamic_avg = statistics_avg_generation_range(min_gen, max_gen, dynamic, sample_size)
#
# progressive = statistics_read_sample_size(file4, fitness_key, sample_size)
# progressive_avg = statistics_avg_generation_range(min_gen, max_gen, progressive, sample_size)

# dynamic_progressive = statistics_read_sample_size(file2, fitness_key, sample_size)
# dynamic_progressive_avg = statistics_avg_generation_range(min_gen, max_gen, dynamic_progressive, sample_size)
#
# # stoc diag
# stoc = statistics_read_sample_size(stoc_diag_f, fitness_key, sample_size)
# stoc_avg = statistics_avg_generation_range(min_gen, max_gen, stoc, sample_size)
#
# dynamic_stoc = statistics_read_sample_size(dyn_sto_f, fitness_key, sample_size)
# dynamic_stoc_avg = statistics_avg_generation_range(min_gen, max_gen, dynamic_stoc, sample_size)

# tec_stoc = statistics_read_sample_size(tecSto, fitness_key, sample_size)
# tec_stoc_avg = statistics_avg_generation_range(min_gen, max_gen, tec_stoc, sample_size)
#
# tec_dist = statistics_read_sample_size(tecDis, fitness_key, sample_size)
# tec_dist_avg = statistics_avg_generation_range(min_gen, max_gen, tec_dist, sample_size)
#
# stoc_hori = statistics_read_sample_size(stoc_hori_f, fitness_key, sample_size)
# stoc_hori_avg = statistics_avg_generation_range(min_gen, max_gen, stoc_hori, sample_size)


# drawing fitness
# x = list(range(0, 1000))
# # multiple line plot
# plt.plot(x, stoc_avg, marker='x', label="stochastic", color='red', linewidth=1, markevery=50, markersize=7)
# plt.plot(x, progressive_avg, marker='^', label="progressive", color='green', linewidth=1, markevery=50, markersize=7)
# plt.xlabel('generation', fontsize=14)
# plt.ylabel('fitness', fontsize=14)
#
# plt.ylim(0.5, 1.00)
# my_x_ticks = np.arange(0, 1001, 100)
# my_y_ticks = np.arange(0.55, 0.96, 0.05)
# plt.xticks(my_x_ticks, fontsize=13)
# plt.yticks(my_y_ticks, fontsize=13)
#
# plt.legend(loc='lower right', fontsize=13)
# plt.tight_layout()
# plt.savefig('/Users/rouyijin/Desktop/fitness_stochastic_vs_dist_progressive_0_1000.png', dpi=500, bbox_inches='tight')
# plt.show()




# modularity
# original_mod = best_mod_avg(file1, min_gen, max_gen, sample_size)
# dynamic_mod = best_mod_avg(file3, min_gen, max_gen, sample_size)
# stochastic_mod = best_mod_avg(file5, min_gen, max_gen, sample_size)
# progressive_mod = best_mod_avg(file4, min_gen, max_gen, sample_size)
# dynamic_progressive_mod = best_mod_avg(file2, min_gen, max_gen, sample_size)
#
dynamic_stoc_mod = best_mod_avg(dyn_sto_f, min_gen, max_gen, sample_size)
stoc_diag_mod = best_mod_avg(stoc_diag_f, min_gen, max_gen, sample_size)
# stoc_hori_mod = best_mod_avg(stoc_hori_f, min_gen, max_gen, sample_size)
#
# # tec
# tec_distributional_mod = best_mod_avg(tecDis, min_gen, max_gen, sample_size)
# tec_stochastic_mod = best_mod_avg(tecSto, min_gen, max_gen, sample_size)
#
# drawing modularity
x = list(range(0, 1000))
# multiple line plot
plt.plot(x, stoc_diag_mod, marker='x', label="stoc pre-defined", color='red', linewidth=1, markevery=50, markersize=7)
plt.plot(x, dynamic_stoc_mod, marker='^', label="stoc dynamic", color='green', linewidth=1, markevery=50, markersize=7)
plt.xlabel('generation', fontsize=14)
plt.ylabel('modularity', fontsize=14)

plt.ylim(-0.2, 1)
my_x_ticks = np.arange(0, 1001, 100)
my_y_ticks = np.arange(-0.20, 1.1, 0.1)
plt.xticks(my_x_ticks, fontsize=13)
plt.yticks(my_y_ticks, fontsize=13)

plt.legend(loc='lower right', fontsize=13)
plt.tight_layout()
plt.savefig('/Users/rouyijin/Desktop/modularity_stochastic_original_vs_dynamic_0_1000.png', dpi=500, bbox_inches='tight')
plt.show()
