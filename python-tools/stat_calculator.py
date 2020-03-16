from GRNCSVReader import GRNCSVReader
from StatisticsToolkit import StatisticsToolkit
from data_storage.data_storage_2020 import *

statistics_toolkit = StatisticsToolkit()
a_path_1 = '/media/zhenyue-qin/New Volume/Experiment-Data-Storage/Storage-Modularity/2020-New-Exps/2020-stochastic-elite-x-p00'
sample_size = 100
fitness_plotter = GRNCSVReader()

fitness_values_1 = fitness_plotter.get_fitness_values_of_an_experiment(a_path_1, -1)[:sample_size]
print fitness_values_1

fits_2 = stoc_x_elite_p00[:sample_size]
print statistics_toolkit.calculate_statistical_significances(fitness_values_1,
                                                             fits_2)


