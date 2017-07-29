import re
import os
import numpy as np
import matplotlib.pyplot as plt
import seaborn as sns
import math

hotspot_pattern = re.compile("(?<=Recombination probability: {)(.*)(?=\})")
rate_pattern = re.compile("(?<=\=)(.*?)(?=, )|(?<=\=)(.*?)(?=})")


def get_immediate_subdirectories(a_dir):
    return [(a_dir + os.sep + name) for name in os.listdir(a_dir)
            if os.path.isdir(os.path.join(a_dir, name))]


def get_grn_hotspot_rates(root_directory_path):
    hotspot_rates = {}
    txt_files = []
    generation = 0

    for root, dirs, files in os.walk(root_directory_path):
        for a_file in files:
            if a_file.endswith(".txt"):
                txt_files.append(root + os.sep + a_file)

    for a_file in txt_files:
        for i, line in enumerate(open(a_file)):
            for match in re.finditer(hotspot_pattern, line):
                for a_rate_match in re.finditer(rate_pattern, match.groups()[0]):
                    if generation not in hotspot_rates:
                        hotspot_rates[generation] = [float(a_rate_match.groups()[0])]
                    else:
                        hotspot_rates[generation].append(float(a_rate_match.groups()[0]))
                generation += 1
    # return sorted(hotspot_rates)
    return hotspot_rates


def plot_hotspot_heat_map(rates):
    x = np.array((rates))
    x_res = x.reshape(1, len(x))

    fig, ax = plt.subplots()
    sns.heatmap(x_res, square=True, ax=ax, cmap=sns.dark_palette("grey", reverse=True))
    plt.yticks(rotation=0, fontsize=16);
    plt.xticks(fontsize=12);
    plt.tight_layout()
    plt.show()


path_1 = "/Users/zhenyueqin/Software-Engineering/COMP4560-Advanced-Computing-Project/Genetic" \
         "-Hotspots/generated-outputs/data-2017-07-18/" \
         "modularized-diploid-seem-work/hotspot-diploid-grn-3-target-10-matrix-evolved-spx/"

sub_directories = get_immediate_subdirectories(path_1)

rates_of_rates = []

rates_of_rates_in_different_experiments = []

rates_sum = []

for a_directory in sub_directories:
    grn_hotspot_rates_dict = get_grn_hotspot_rates(a_directory)
    for key in sorted(grn_hotspot_rates_dict):
        rates_of_rates.append(grn_hotspot_rates_dict[key])
    rates_of_rates_in_different_experiments.append(rates_of_rates[-1])
    rates_sum = [sum(x) for x in zip(*rates_of_rates)]
    plot_hotspot_heat_map(rates_sum)
