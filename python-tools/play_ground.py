from StatisticsToolkit import StatisticsToolkit

path_1 = '/Users/qin/Software-Engineering/Chin-GA-Project/generated-outputs/' \
               'all-combination-perturbations/'

path_2 = '/Users/qin/Software-Engineering/Chin-GA-Project/generated-outputs/' \
               'all-combination-perturbations-asymmetric/'

omega = StatisticsToolkit(path_1, path_2)

print omega.calculate_fitness_significance(sample_size=40)
