from StatisticsToolkit import StatisticsToolkit
from GRNCSVReader import GRNCSVReader
import os
from CSVFileOpener import CSVFileOpener
import file_processor as fp

csv_file_opener = CSVFileOpener()

prefix_path = os.path.expanduser("~")

fitness_plotter = GRNCSVReader()

path_1 = '/Volumes/Qin-Warehouse/Warehouse-Data/Modularity-Data/Maotai-Project-Symmetry-Breaking/generated-outputs/fixed-record-zhenyue-balanced-combinations-p00'
# path_1 = '/Volumes/Qin-Warehouse/Warehouse-Data/Modularity-Data/Maotai-Project-Symmetry-Breaking/generated-outputs/record-esw-balanced-combinations-p00'

sample_size = 100
most_mod_values_1 = fitness_plotter.get_fittest_modularities_of_an_experiment(path_1, 500)[:sample_size]
most_mod_values_2 = fitness_plotter.get_fittest_modularities_of_an_experiment(path_1, -1)[:sample_size]

mods = csv_file_opener.get_properties_of_each_generation_in_a_whole_experiment(path_1, 'FittestModularity')

print StatisticsToolkit.calculate_statistical_significances(most_mod_values_1, most_mod_values_2)

fp.save_lists_graph([mods], labels=['modularity'], ver_lines=[500], path=path_1, file_name='modularity.png', marker='.',
                    colors=[0], dpi=500, to_normalize=False)