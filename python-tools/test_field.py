from EdgeNumberTool import EdgeNumberTool
from GRNCSVReader import GRNCSVReader
from scipy.stats.stats import pearsonr


path_1 = '/Volumes/LaCie/Maotai-Project-Symmetry-Breaking/generated-outputs/record-zhenyue-balanced-combinations-p00'
path_2 = '/Volumes/LaCie/Maotai-Project-Symmetry-Breaking/generated-outputs/record-zhenyue-balanced-combinations-p001'

sample_size = 100

edge_number_tool = EdgeNumberTool()
inter_module_edge_1 = edge_number_tool.get_average_inter_module_edges_for_an_experiment(
    path_1, 'fit', sample_size)[:sample_size]

fitness_plotter = GRNCSVReader()

fitness_values_1 = fitness_plotter.get_fitness_values_of_an_experiment(path_1, -1)[:sample_size]

print("fitness values: ", fitness_values_1)

print "pearsonr: ", pearsonr(inter_module_edge_1, fitness_values_1)