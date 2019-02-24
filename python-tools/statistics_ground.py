from StatisticsToolkit import StatisticsToolkit
import os

prefix_path = os.path.expanduser("~")

path_1 = '/Volumes/Qin-Warehouse/Warehouse-Data/Modularity-Data/Maotai-Project-Symmetry-Breaking/generated-outputs/fixed-record-zhenyue-balanced-combinations-p00'
path_2 = '/Volumes/Qin-Warehouse/Warehouse-Data/Modularity-Data/Maotai-Project-Symmetry-Breaking/generated-outputs/fixed-record-zhenyue-balanced-combinations-p01'
# path_2 = '/Volumes/Qin-Warehouse/Warehouse-Data/Modularity-Data/Maotai-Project-Symmetry-Breaking/generated-outputs/record-esw-balanced-combinations-p00'
# path_1 = '/Users/qin/Portal/generated-outputs/no-selection'
# path_2 = '/Users/qin/Portal/generated-outputs/with-selection'

omega = StatisticsToolkit(path_1, path_2)

sample_size = 50

the_index = -1

print "path 1", path_1
print "path 2", path_2

print('fitness significance')
print omega.calculate_fitness_significance(sample_size=sample_size, index=the_index)

print('most modularity significance')
print omega.calculate_most_modularities_significance(sample_size=sample_size, index=the_index)

print('fittest modularity significance')
print omega.calculate_fittest_modularities_significance(sample_size=sample_size, index=the_index)

print('modularity significance')
print omega.calculate_modularity_significance(sample_size=sample_size)

print('edge number significance')
print omega.calculate_edge_number_significance(sample_size=sample_size)

print('edge number std dev significance')
print omega.calculate_edge_number_std_dev_significance(sample_size=sample_size)

print('inter module edge number significance')
print(omega.calculate_inter_module_edge_number_significance(phenotype_type='fit', sample_size=sample_size))

# print omega.calcuate_evaluated_inter_module_edge_number_significance(phenotype_type='fit', eva_type='mode',
#                                                                      sample_size=sample_size, start_gen=501,
#                                                                      end_gen=2000, to_plot=True)
