from FitnessPlotter import FitnessPlotter
from StatisticsToolkit import StatisticsToolkit


path_1 = '/Users/qin/Software-Engineering/Chin-GA-Project/generated-outputs/change-to-tournament-selection'
omega = FitnessPlotter()
a = omega.get_best_fitness_values_of_an_exeriment(path_1)[:40]

path_2 = '/Users/qin/Software-Engineering/Chin-GA-Project/thesis-data/combined-chin-crossover'
b = omega.get_best_fitness_values_of_an_exeriment(path_2)[:40]

beta = StatisticsToolkit()
print beta.calculate_statistical_significances(a, b)

