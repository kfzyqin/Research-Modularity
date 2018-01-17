from GRNPlotter import GRNPlotter

grn_plotter = GRNPlotter()

a_directory = '/Users/qin/Software-Engineering/Chin-GA-Project/thesis-data/' \
               'combined-chin-crossover/2017-11-21-15-07-56/'
# grn_plotter.get_module_values_of_a_trial(a_directory, True, True, [500])
phes = grn_plotter.get_grn_phenotypes(a_directory)
grn_plotter.draw_a_grn(phes[1999], save_path=a_directory, file_name='1999-modular.png')
