import os
import matplotlib.pyplot as plt


def get_immediate_subdirectories(a_dir):
    return [(a_dir + os.sep + name) for name in os.listdir(a_dir)
            if os.path.isdir(os.path.join(a_dir, name))]


def save_a_list_graph(a_list, y_label, path, file_name):
    plt.plot(a_list)
    plt.ylabel(y_label)
    plt.savefig(path + os.sep + file_name, dpi=200)
    plt.close()


def save_multiple_lists_graph(lists, labels, path, file_name, vertical_lines=None):
    if vertical_lines is None:
        vertical_lines = []
    for idx in range(len(lists)):
        plt.plot(lists[idx], label=labels[idx])
    if len(vertical_lines) != 2:
        for xc in vertical_lines:
            plt.axvline(x=xc)
    else:
        plt.axvline(x=vertical_lines[0], color='green', linestyle='--')
        plt.axvline(x=vertical_lines[1], color='red', linestyle='--')

    plt.legend()
    plt.savefig(path + os.sep + file_name, dpi=200)
    plt.close()


def count_number_of_edges(a_grn):
    return sum(x != 0 for x in a_grn)
