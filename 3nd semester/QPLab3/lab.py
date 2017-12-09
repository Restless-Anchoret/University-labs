import numpy as np
from scipy.integrate import odeint
from scipy.misc import derivative
import scipy.integrate as integrate
from scipy.interpolate import interp1d
import matplotlib.pyplot as plt
from scipy.special import genlaguerre
import math

# --------------------- Data ----------------------------
lagfun = genlaguerre(10, 3.5)
# initial data (atomic units)
clength = 0.5292
cenergy = 27.212
L = 3.0 / clength
A = -L
B = +L
v0 = 0.1 / cenergy
# v0 = 25.0 / cenergy
# number of mesh node
n = 1001  # odd integer number
print("n=", n)
# minimum of potential (atomic units) - for visualization only!
Umin = -0.2
# maximum of potential (atomic units) - for visualization only!
W = 2.2
# x-coordinates of the nodes
X  = np.linspace(A, B, n)  # forward
XX = np.linspace(B, A, n)  # backwards
# node of sewing
r = (n-1)*3//4      # forward
rr = n-r-1          # backwards
print("r=", r)
print("rr=", rr)
print("X[r]=", X[r])
print("XX[rr]=", XX[rr])
pot = lambda x: x
normed_func = []
energies = []
# --------------------- Data ----------------------------

# potential function
def U(x):
    return v0 * lagfun(abs(x)) if abs(x) < L else W


# function (13)
def q(e, x):
    global pot
    return 2.0*(e-pot(x))


def system1(cond1, X):
    global eee
    Y0, Y1 = cond1[0], cond1[1]
    dY0dX = Y1
    dY1dX = - q(eee, X)*Y0
    return [dY0dX, dY1dX]


def system2(cond2, XX):
    global eee
    Z0, Z1 = cond2[0], cond2[1]
    dZ0dX = Z1
    dZ1dX = - q(eee, XX)*Z0
    return [dZ0dX, dZ1dX]


# calculation of f (eq. 18; difference of derivatives)
def f_fun(e):
    global r, n, Psi, Fi, X, XX, eee
    eee = e
    """
    Cauchy problem ("forward")
    dPsi1(x)/dx = - q(e, x)*Psi(x);
    dPsi(x)/dx = Psi1(x);
    Psi(A) = 0.0
    Psi1(A)= 1.0
    """
    cond1 = [0.0, 1.0]
    sol1 = odeint(system1, cond1, X)
    Psi, Psi1 = sol1[:, 0], sol1[:, 1]
    """
    Cauchy problem ("backwards")
    dPsi1(x)/dx = - q(e, x)*Psi(x);
    dPsi(x)/dx = Psi1(x);
    Psi(B) = 0.0
    Psi1(B)= 1.0
    """
    cond2 = [0.0, 1.0]
    sol2 = odeint(system2, cond2, XX)
    Fi, Fi1 = sol2[:, 0], sol2[:, 1]
    # search of maximum value of Psi
    p1 = np.abs(Psi).max()
    p2 = np.abs(Psi).min()
    big = p1 if p1 > p2 else p2
    # scaling of Psi
    Psi[:] = Psi[:]/big
    # mathematical scaling of Fi for F[rr]=Psi[r]
    coef = Psi[r]/Fi[rr]
    Fi[:] = coef * Fi[:]
    # calculation of f(E) in node of sewing
    curve1 = interp1d(X, Psi, kind='cubic', bounds_error=False, fill_value="extrapolate")
    curve2 = interp1d(XX, Fi, kind='cubic', bounds_error=False, fill_value="extrapolate")
    der1 = derivative(curve1, X[r], dx=1.e-6)
    der2 = derivative(curve2, XX[rr], dx=1.e-6)
    f = der1-der2
    return f


def m_bis(x1, x2, tol):
    global r, n
    if f_fun(e=x2)*f_fun(e=x1) > 0.0:
        print("ERROR: f_fun(e=x2, r, n)*f_fun(e=x1, r, n) > 0")
        print("x1=", x1)
        print("x2=", x2)
        print("f_fun(e=x1, r=r, n=n)=", f_fun(e=x1))
        print("f_fun(e=x2, r=r, n=n)=", f_fun(e=x2))
        exit()
    while abs(x2-x1) > tol:
        xr = (x1+x2)/2.0
        if f_fun(e=x2)*f_fun(e=xr) < 0.0:
            x1 = xr
        else:
            x2 = xr
        if f_fun(e=x1)*f_fun(e=xr) < 0.0:
            x2 = xr
        else:
            x1 = xr
    return (x1+x2)/2.0


def integral_num(num_fun):
    curve = interp1d(X, num_fun, kind='cubic', bounds_error=False, fill_value="extrapolate")
    return integral_curve(curve)


def integral_curve(curve):
    result = integrate.quad(curve, A, B, limit=500)
    if result[1] > 1.0e-3:
        print("Warning! Not enough accuracy for integral calculation:", result[1])
    return result[0]


def normalize_func(num_fun):
    guad_fun = [i * i for i in num_fun]
    n_value = integral_num(guad_fun)
    # print("N =", n_value)
    n_value_quad_root = pow(n_value, 0.5)
    # print("N quad =", n_value_quad_root)
    normed_fun = [i / n_value_quad_root for i in num_fun]
    return interp1d(X, normed_fun, kind='cubic', bounds_error=False, fill_value="extrapolate")


def get_phi(k):
    if k % 2 == 0:
        return lambda x: 1 / math.sqrt(L) * math.cos(math.pi * (k + 1) * x / (2 * L))
    else:
        return lambda x: 1 / math.sqrt(L) * math.sin(math.pi * (k + 1) * x / (2 * L))


def get_psi(coef_list):
    phi_list = tuple(get_phi(i) for i in range(len(coef_list)))
    sign = np.sign(coef_list[0])
    return lambda x: math.fsum(sign * coef * phi_i(x) for phi_i, coef in zip(phi_list, coef_list))


def compute_matrix_H(dim=10):
    H = np.zeros((dim, dim))
    for i in range(dim):
        for j in range(dim):
            phi_i = get_phi(i)
            phi_j = get_phi(j)
            factor = 0.5 * (math.pi * (j + 1) / (2 * L)) ** 2
            H[i, j], error = integrate.quad(lambda x: phi_i(x) * phi_j(x) * (factor + U(x)), A, B, limit=500)

    return H


def draw_plot(base_energy, base_func, computed_energies, computed_funcs, n_values):
    global X
    Upot = to_num(U)
    plt.plot(X, Upot, 'g-', linewidth=2.0, label="U(x)")
    Zero = np.zeros(n, dtype=float)
    plt.plot(X, Zero, 'k-', linewidth=1.0)  # abscissa axis
    plt.plot(X, base_func, 'y-', linewidth=4.0, label="Psi(x)")
    plt.figtext(0.01, 0.9, "E (spotting method) = " + format(base_energy, "10.7f"), fontsize=14, color='black')
    colors = ['b-', 'r-']
    widths = [3.0, 2.0]
    for i in range(len(n_values)):
        plt.plot(X, computed_funcs[i], colors[i], linewidth=widths[i], label="Psi(x) (N = " + format(n_values[i], "2d") + ")")
        plt.figtext(0.01, 0.8 - (i * 0.1), "E (N = " + format(n_values[i], "2d") + ") = " + format(computed_energies[i], "10.7f"), fontsize=14, color='black')
    plt.xlabel("X", fontsize=18, color="k")
    plt.ylabel("Psi(x), U(x)", fontsize=18, color="k")
    plt.grid(True)
    plt.legend(fontsize=16, shadow=True, fancybox=True, loc='upper right')

    plt.savefig("result.pdf", dpi=300)
    plt.show()


def count_energies(number, potential):
    global pot
    # plot of f(e)
    e1 = Umin + 0.0005
    e2 = 10.0
    # e2 = 5.0
    print("e1=", e1, "   e2=", e2)
    # ne = 401
    ne = 601
    print("ne=", ne)
    ee = np.linspace(e1, e2, ne)
    af = np.zeros(ne, dtype=float)
    porog = 5.0
    tol = 1.0e-7
    energy = []
    func = []
    pot = potential
    for i in np.arange(ne):
        e = ee[i]
        af[i] = f_fun(e)
        stroka = "i = {:3d}   e = {:8.5f}  f[e] = {:12.5e}"
        print(stroka.format(i, e, af[i]))
        if i > 0:
            Log1 = af[i] * af[i - 1] < 0.0
            Log2 = np.abs(af[i] - af[i - 1]) < porog
            if Log1 and Log2:
                energy1 = ee[i - 1]
                energy2 = ee[i]
                eval = m_bis(energy1, energy2, tol)
                print("eval = {:12.5e}".format(eval))
                # plotting_wf(eval)
                energy.append(eval)
                func.append(Psi)
        if len(energy) == number:
            break

    nroots = len(energy)
    print("nroots =", nroots)
    for i in np.arange(nroots):
        stroka = "i = {:1d}    energy[i] = {:12.5e}"
        print(stroka.format(i, energy[i]))

    return energy, func


def to_num(func):
    global X
    return [func(x) for x in X]


def main():
    global energies, normed_func

    energies, func = count_energies(1, U)
    normed_func = [normalize_func(num_fun) for num_fun in func]
    # energies = [0.13157468096494676, 0.15949321705102915, 0.51551614838361748, 1.1801860681319241, 1.4219754865670209,
    #             2.0787318099570271, 2.5224866503643986, 3.2763789475798601, 3.9198726021170618, 4.7911556995463371]
    print("Energies by spotting method:", energies)

    computed_energies = []
    computed_funcs = []
    n_values = [5, 15]

    for N in n_values:
        H = compute_matrix_H(N)
        values, vectors = np.linalg.eig(H)
        print(values, vectors)
        E, vector = min(zip(values, (vectors[:, i] for i in range(len(values)))), key=lambda x: x[0])
        print(E, vector)
        computed_energies.append(E)
        computed_funcs.append(to_num(get_psi(vector)))

    draw_plot(energies[0], to_num(normed_func[0]), computed_energies, computed_funcs, n_values)


main()
