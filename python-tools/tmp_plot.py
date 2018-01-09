from GRNPlotter import GRNPlotter

path_1 = '/Users/qin/Software-Engineering/Chin-GA-Project/thesis-data/' \
               'elitism-reduce-modularity/'

omega = GRNPlotter()

omega.get_module_values_of_an_experiment(path_1, draw_gen_avg_modularity=True, louvain=True)