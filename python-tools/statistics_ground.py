from StatisticsToolkit import StatisticsToolkit
import os

prefix_path = os.path.expanduser("~")

path_1 = '/home/zhenyue-qin/Research/Project-Kuroko-Portal/Project-Maotai-Portal/generated-outputs/stochastic-p00'
path_2 = '/home/zhenyue-qin/Research/Project-Kuroko-Portal/Project-Maotai-Portal/generated-outputs/stochastic-p01'

omega = StatisticsToolkit(path_1, path_2)

sample_size = 100

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
print omega.calculate_modularity_significance(sample_size=sample_size, no_self_edge=True)

print('edge number significance')
print omega.calculate_edge_number_significance(sample_size=sample_size)

# print('edge number std dev significance')
# print omega.calculate_edge_number_std_dev_significance(sample_size=sample_size)
#
# print('inter module edge number significance')
# print(omega.calculate_inter_module_edge_number_significance(phenotype_type='fit', sample_size=sample_size))

# print omega.calcuate_evaluated_inter_module_edge_number_significance(phenotype_type='fit', eva_type='mode',
#                                                                      sample_size=sample_size, start_gen=501,
#                                                                      end_gen=2000, to_plot=True)
