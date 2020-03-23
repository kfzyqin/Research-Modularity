from GRNCSVReader import GRNCSVReader
from StatisticsToolkit import StatisticsToolkit
from data_storage.data_storage_2020 import *
import statistics

statistics_toolkit = StatisticsToolkit()
# a_path_1 = '/media/zhenyue-qin/New Volume/Experiment-Data-Storage/Storage-Modularity/2020-New-Exps/2020-stochastic-x-p00'
sample_size = 100
# fitness_plotter = GRNCSVReader()
#
# fitness_values_1 = fitness_plotter.get_fitness_values_of_an_experiment(a_path_1, -1)[:sample_size]
# print fitness_values_1

fits_1 = dist_x_p00_by_dist_p00_final
fits_2 = dist_elite_x_p00_by_dist_p00_final

fits_3 = stoc_x_p00_by_stoc_p00_final
fits_4 = stoc_elite_x_p00_by_stoc_p00_final
# print statistics_toolkit.calculate_statistical_significances(fits_1,
#                                                              fits_2)
for a in [fits_1, fits_2, fits_3, fits_4]:
    print statistics.mean(a)

