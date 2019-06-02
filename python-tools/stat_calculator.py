from StatisticsToolkit import StatisticsToolkit
import tools.storage.stat_warehouse as stat_warehouse
import tools.storage.fitness_warehouse as fitness_warehouse

statistics_toolkit = StatisticsToolkit()
print statistics_toolkit.calculate_statistical_significances(fitness_warehouse.stoc_p01_by_dist_p01_gen,
                                                             fitness_warehouse.dist_p01_by_dist_p01_gen)