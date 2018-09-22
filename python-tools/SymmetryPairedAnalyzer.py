from StatisticsToolkit import StatisticsToolkit
import os
from tabulate import tabulate
from time import gmtime, strftime


class SymmetryPairedAnalyzer:
    def __init__(self):
        self.prefix_path = os.path.expanduser("~")
        self.candidates = ['p00', 'p01']
        # self.starting_path = self.prefix_path + '/Portal/generated-outputs/combinations-'
        self.starting_path = self.prefix_path + '/Portal/generated-outputs/record-zhenyue-balanced-combinations-'
        self.sample_size = 55

    def print_a_paired_dist(self, a_dict):
        tab_list = []
        headers = [' '] + self.candidates
        for i in range(len(self.candidates)):
            new_row_list = []
            for j in range(len(self.candidates)):
                if j <= i:
                    if j == 0:
                        new_row_list.append(self.candidates[i])
                        new_row_list.append(' ')
                    else:
                        new_row_list.append(' ')
                else:
                    new_row_list.append(a_dict[(self.candidates[i], self.candidates[j])])
            tab_list.append(new_row_list)
        return tabulate(tab_list, headers=headers, tablefmt='orgtbl')

    def print_a_single_dist(self, a_dict):
        tab_list = []
        headers = self.candidates
        new_row_list = []
        for i in range(len(self.candidates)):
            new_row_list.append(a_dict[self.candidates[i]])
        tab_list.append(new_row_list)
        return tabulate(tab_list, headers=headers, tablefmt='orgtbl')

    def paired_calculate(self):
        fittest_dist = {}
        most_mod_dist = {}
        fittest_mod_dist = {}

        fittest_pair_dist = {}
        most_mod_pair_dist = {}
        fittest_mod_pair_dist = {}

        for i in range(len(self.candidates)):
            for j in range(i+1, len(self.candidates)):
                path_1 = self.starting_path + self.candidates[i]
                path_2 = self.starting_path + self.candidates[j]
                omega = StatisticsToolkit(path_1, path_2)
                fitness_stat = omega.calculate_fitness_significance(sample_size=self.sample_size)
                most_mod_stat = omega.calculate_most_modularities_significance(sample_size=self.sample_size)
                fittest_mod_stat = omega.calculate_fittest_modularities_significance(sample_size=self.sample_size)

                fittest_dist[self.candidates[i]] = fitness_stat['average_1']
                most_mod_dist[self.candidates[i]] = most_mod_stat['average_1']
                fittest_mod_dist[self.candidates[i]] = fittest_mod_stat['average_1']

                fittest_dist[self.candidates[j]] = fitness_stat['average_2']
                most_mod_dist[self.candidates[j]] = most_mod_stat['average_2']
                fittest_mod_dist[self.candidates[j]] = fittest_mod_stat['average_2']

                fittest_pair_dist[(self.candidates[i], self.candidates[j])] = fitness_stat['wilcoxon'][1]
                most_mod_pair_dist[(self.candidates[i], self.candidates[j])] = most_mod_stat['wilcoxon'][1]
                fittest_mod_pair_dist[(self.candidates[i], self.candidates[j])] = fittest_mod_stat['wilcoxon'][1]

        cur_time = strftime("-%Y-%m-%d-%H-%M-%S", gmtime())
        f = open("./miscellaneous/Statistics-Report" + cur_time + ".txt", "w+")
        f.write("\n" + self.starting_path + "\n")

        f.write("\n" + "sample size " + str(self.sample_size) + "\n")

        f.write("\nfittest dist\n")
        f.write(self.print_a_single_dist(fittest_dist))

        f.write("\nmost mod dist\n")
        f.write(self.print_a_single_dist(most_mod_dist))

        f.write("\nfittest mod dist\n")
        f.write(self.print_a_single_dist(fittest_mod_dist))

        f.write("\nfittest pair dist\n")
        f.write(self.print_a_paired_dist(fittest_pair_dist))

        f.write("\nmost mod pair dist\n")
        f.write(self.print_a_paired_dist(most_mod_pair_dist))

        f.write("\nfittest mod pair dist\n")
        f.write(self.print_a_paired_dist(fittest_mod_pair_dist))


symmetry_paired_analyzer = SymmetryPairedAnalyzer()
symmetry_paired_analyzer.paired_calculate()

