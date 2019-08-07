import storage.edge_num_shading as edge_num_shading
import matplotlib.pyplot as plt
import seaborn as sns
import numpy as np
import ast
import re
import os

def plot_grn_edge_gen_shading(edge_gen_shading_list, saving_path=None):
    fig, ax = plt.subplots()
    plt.xlabel('')
    plt.ylabel('')
    the_y_tick_labels = list(range(10, 50))
    # sns.heatmap(x_res, square=True, ax=ax, cmap=sns.dark_palette("grey", reverse=True), yticklabels=False)
    if isinstance(edge_gen_shading_list, np.ndarray):
        an_edge_num_gen = edge_gen_shading_list
    else:
        an_edge_num_gen = np.array(edge_gen_shading_list).transpose()
    sns.heatmap(an_edge_num_gen, yticklabels=the_y_tick_labels, cmap="CMRmap").invert_yaxis()
    plt.xticks(fontsize=12)
    plt.legend(loc='lower center')
    plt.tight_layout()
    if saving_path is None:
        plt.show()
    else:
        plt.savefig(saving_path)
        plt.close()

def plot_overall_edge_density(target_path, save_path=None):
    target_f = open(target_path)
    f_txt = target_f.read()
    phenotype_patter = re.compile("(?<=\[\[\[)(.*)(?=\]\]\])")
    finals = []
    for match in re.finditer(phenotype_patter, f_txt):
        finals.append(match.groups())
    tmp_str_list = ast.literal_eval('[[[' + finals[0][0] + ']]]')
    edge_density_overall_list = list([[[float(k) for k in j] for j in i] for i in tmp_str_list])
    edge_density_overall_np = np.array(edge_density_overall_list)
    # plot_grn_edge_gen_shading(np.mean(edge_density_overall_np, axis=0).transpose())

    for i in range(1, 21):
        saving_file_name = os.path.join(save_path, 'edge_shading_' + str(i) + '.png')
        plot_grn_edge_gen_shading(edge_density_overall_np[i].transpose(), saving_path=saving_file_name)


if __name__ == '__main__':
    # plot_grn_edge_gen_shading(edge_num_shading.dist_p00_edge_num_shading)
    # plot_grn_edge_gen_shading(edge_num_shading.dist_prop_edge_num_shading)

    # print np.array(edge_num_shading.dist_prop_edge_num_shading).sum(axis=1)
    target_1_path = '/home/zhenyue-qin/Research/Project-Rin-Datasets/Project-Maotai-Data/Tec-Simultaneous-Experiments' \
                    '/edge-shading-logs/distributional-proportional-global-optimum-mod-shading.txt '
    target_2_path = '/home/zhenyue-qin/Research/Project-Rin-Datasets/Project-Maotai-Data/Tec-Simultaneous-Experiments' \
                    '/edge-shading-logs/distributional-tournament-global-optimum-mod-shading.txt '

    save_path_1 = '/home/zhenyue-qin/Research/Project-Rin-Datasets/Project-Maotai-Data/Tec-Simultaneous-Experiments' \
                  '/edge-shading-logs/distributional-proportional-shadings/mod '
    save_path_2 = '/home/zhenyue-qin/Research/Project-Rin-Datasets/Project-Maotai-Data/Tec-Simultaneous-Experiments' \
                  '/edge-shading-logs/distributional-tournament-shadings/mod '
    # plot_overall_edge_density(target_1_path, save_path=save_path_1)
    plot_overall_edge_density(target_2_path, save_path=save_path_2)
