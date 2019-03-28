from StatisticsToolkit import StatisticsToolkit
from GRNCSVReader import GRNCSVReader
import os
from CSVFileOpener import CSVFileOpener
import file_processor as fp

csv_file_opener = CSVFileOpener()

prefix_path = os.path.expanduser("~")

fitness_plotter = GRNCSVReader()

path_1 = '/Volumes/Qin-Warehouse/Warehouse-Data/Modularity-Data/Maotai-Project-Symmetry-Breaking/generated-outputs/fixed-record-zhenyue-balanced-combinations-p00'
path_2 = '/Volumes/Qin-Warehouse/Warehouse-Data/Modularity-Data/Maotai-Project-Symmetry-Breaking/generated-outputs/record-esw-balanced-combinations-p00'

# path_1 = '/Volumes/Qin-Warehouse/Warehouse-Data/Modularity-Data/Maotai-Project-Symmetry-Breaking/generated-outputs/fixed-record-zhenyue-balanced-combinations-elite-p00'
#
# from GRNPlotter import GRNPlotter
#
# grn_plotter = GRNPlotter()
# mod_1 = grn_plotter.get_avg_module_values_for_each_generation_of_an_experiment(path_1, no_self_edge=True)
# # mod_2 = grn_plotter.get_avg_module_values_for_each_generation_of_an_experiment(path_2, no_self_edge=True)
#
# print('mod 1: ', len(mod_1))
# fp.save_lists_graph([mod_1], labels=['Modularity'], ver_lines=[500], path=path_1, file_name='avg_mod_no_self_edge.png', marker='.',
#                     colors=None, dpi=500, to_normalize=False)

sample_size = 100
most_mod_values_1 = fitness_plotter.get_fittest_modularities_of_an_experiment(path_1, 500)[:sample_size]
most_mod_values_2 = fitness_plotter.get_fittest_modularities_of_an_experiment(path_1, -1)[:sample_size]

mods_1 = csv_file_opener.get_properties_of_each_generation_in_a_whole_experiment(path_1, 'FittestModularity')
mods_2 = csv_file_opener.get_properties_of_each_generation_in_a_whole_experiment(path_1, 'FittestModularity')
#
# print StatisticsToolkit.calculate_statistical_significances(most_mod_values_1, most_mod_values_2)

fp.save_lists_graph([mods_1], labels=['Modularity'], ver_lines=[500], path=path_1, file_name='modularity.png', marker='.',
                    colors=[0], dpi=500, to_normalize=False)
