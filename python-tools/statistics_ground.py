from StatisticsToolkit import StatisticsToolkit

path_1 = '/Users/qin/Software-Engineering/Chin-GA-Project/generated-outputs/' \
               'larson-with-perturbation-recording/'

path_2 = '/Users/qin/Software-Engineering/Chin-GA-Project/generated-outputs/' \
               'soto-with-perturbation-recording'

omega = StatisticsToolkit(path_1, path_2)

print "path 1", path_1
print "path 2", path_2

print omega.calculate_fitness_significance(sample_size=40)
print omega.calculate_modularity_significance(sample_size=40)

