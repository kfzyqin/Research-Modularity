from EdgeNumberTool import EdgeNumberTool
from GRNCSVReader import GRNCSVReader
import file_processor as fp
path_1 = '/Users/qin/Portal/generated-outputs/fixed-record-zhenyue-balanced-combinations-p00'
path_2 = '/Users/qin/Portal/generated-outputs/fixed-record-zhenyue-balanced-combinations-p001'

phenotype_type = 'fit'
sample_size = 100

edge_number_tool = EdgeNumberTool()
grn_csv_reader = GRNCSVReader()

# inter_module_edge_1 = edge_number_tool.get_average_inter_module_edges_for_an_experiment(path_1, phenotype_type,
#                                                                                         sample_size=sample_size)[:sample_size]
# inter_module_edge_2 = edge_number_tool.get_average_inter_module_edges_for_an_experiment(path_2, phenotype_type,
#                                                                                         sample_size=sample_size)[:sample_size]
#
# intra_module_edge_1 = edge_number_tool.get_average_intra_module_edges_for_an_experiment(path_1, phenotype_type,
#                                                                                         sample_size=sample_size)[:sample_size]
# intra_module_edge_2 = edge_number_tool.get_average_intra_module_edges_for_an_experiment(path_2, phenotype_type,
#                                                                                         sample_size=sample_size)[:sample_size]

# fitness_values_1 = grn_csv_reader.get_fitness_values_of_an_experiment(path_1, -1)[:sample_size]
# fitness_values_2 = grn_csv_reader.get_fitness_values_of_an_experiment(path_2, -1)[:sample_size]
#
# print(max(fitness_values_1))
# print(max(fitness_values_2))

# fp.save_lists_graph([sorted(inter_module_edge_1), sorted(intra_module_edge_1), sorted(fitness_values_1)], ['inter', 'intra', 'fit'], marker='x',
#                     to_normalize=False)
# fp.save_lists_graph([sorted(inter_module_edge_2), sorted(intra_module_edge_2), sorted(fitness_values_2)], ['inter', 'intra', 'fit'], marker='x',
#                     to_normalize=False)



