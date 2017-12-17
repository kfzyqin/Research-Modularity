import os
import matplotlib.pyplot as plt
import operator as op
import math


def ncr(n, r):
    r = min(r, n-r)
    if r == 0: return 1
    numer = reduce(op.mul, xrange(n, n-r, -1))
    denom = reduce(op.mul, xrange(1, r+1))
    return numer//denom


def get_immediate_subdirectories(a_dir):
    return [(a_dir + os.sep + name) for name in os.listdir(a_dir)
            if os.path.isdir(os.path.join(a_dir, name))]


def save_a_list_graph(a_list, y_label, path, file_name):
    plt.plot(a_list)
    plt.ylabel(y_label)
    plt.savefig(path + os.sep + file_name, dpi=200)
    plt.close()


def plot_a_list_graph(a_list, y_label):
    plt.plot(a_list)
    plt.ylabel(y_label)
    plt.show()


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

    plt.legend(fontsize=8, loc=8)
    plt.savefig(path + os.sep + file_name, dpi=200)
    plt.close()


def count_number_of_edges(a_grn):
    return sum(x != 0 for x in a_grn)


def calculate_binomial_distribution(n, p, to_plot=False):
    prob_list = []
    for i in range(n+1):
        combination = ncr(n, i)
        a_result = combination * math.pow(p, i) * math.pow((1-p), (n-i))
        prob_list.append(a_result)
        print "When k is ", i, "\t the probability is ", a_result
    if to_plot:
        plot_a_list_graph(prob_list, 'Probability')


def write_a_list_into_a_file(a_list, file_path, file_name):
    the_file = open(file_path + os.sep + file_name, 'w')
    for item in a_list:
        the_file.write("%s " % item)

