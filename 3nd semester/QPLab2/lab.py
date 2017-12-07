import numpy as np
from scipy.integrate import odeint
from scipy.misc import derivative
import scipy.integrate as integrate
from scipy.interpolate import interp1d
import matplotlib.pyplot as plt
from scipy.special import genlaguerre
import mpmath

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
W = 3.0
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
def U0(x):
    return v0 * lagfun(abs(x)) if abs(x) < L else W
    # return v0 * lagfun(abs(x))


def V(x):
    return 0.15 if abs(x - 1) < L / 30 else 0


def U(x):
    return U0(x) + V(x)


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

def plotting_u(potential):
    plt.axis([A, B, Umin, W])
    Upot = np.array([potential(X[i]) for i in np.arange(n)])
    plt.plot(X, Upot, 'g-', linewidth=2.0, label="U(x)")
    plt.show()

# def plotting_f():
#     plt.axis([U0, e2, fmin, fmax])
#     ZeroE = np.zeros(ne, dtype=float)
#     plt.plot(ee, ZeroE, 'k-', linewidth=1.0)  # abscissa axis
#     plt.plot(ee, af, 'bo', markersize=1)
#     plt.xlabel("E", fontsize=18, color="k")
#     plt.ylabel("f(E)", fontsize=18, color="k")
#     plt.grid(True)
#     # save to file
#     plt.savefig('schrodinger-2b-f.pdf', dpi=300)
#     plt.show()


# def plotting_wf(e):
#     global r, n, Psi, Fi, X, XX
#     ff = f_fun(e)
#     plt.axis([A, B, -3.0, W])
#     Upot = np.array([U(X[i]) for i in np.arange(n)])
#     plt.plot(X, Upot, 'g-', linewidth=2.0, label="U(x)")
#     Zero = np.zeros(n, dtype=float)
#     plt.plot(X, Zero, 'k-', linewidth=1.0)  # abscissa axis
#     plt.plot(X, Psi, 'r-', linewidth=2.0, label="Psi(x)")
#     plt.plot(XX, Fi, 'b-', linewidth=2.0, label="Fi(x)")
#     plt.xlabel("X", fontsize=18, color="k")
#     plt.ylabel("Psi(x), Fi(x), U(x)", fontsize=18, color="k")
#     plt.grid(True)
#     plt.legend(fontsize=16, shadow=True, fancybox=True, loc='upper right')
#     plt.plot([X[r]], [Psi[r]], color='red', marker='o', markersize=7)
#     string1 = "E    = " + format(e, "10.7f")
#     string2 = "f(E) = " + format(ff, "10.3e")
#     plt.text(-4.0, 2.7, string1, fontsize=14, color='black')
#     plt.text(-4.0, 2.3, string2, fontsize=14, color="black")
#     # save to file
#     name = "schrodinger-2b" + "-" + str(ngr) + ".pdf"
#     plt.savefig(name, dpi=300)
#     plt.show()


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


def px(num_fun):
    curve = interp1d(X, num_fun, kind='cubic', bounds_error=False, fill_value="extrapolate")
    der_num = [derivative(curve, x, dx=1.e-6) for x in X]
    der_curve = interp1d(X, der_num, kind='cubic', bounds_error=False, fill_value="extrapolate")
    integral_fun = lambda x: curve(x) * der_curve(x)
    integral_val = integral_curve(integral_fun)
    return complex(0, -integral_val)


def px2(num_fun):
    curve = interp1d(X, num_fun, kind='cubic', bounds_error=False, fill_value="extrapolate")
    der_num = [derivative(curve, x, dx=1.e-6, n=2) for x in X]
    der_curve = interp1d(X, der_num, kind='cubic', bounds_error=False, fill_value="extrapolate")
    integral_fun = lambda x: curve(x) * der_curve(x)
    integral_val = integral_curve(integral_fun)
    return -integral_val


def get_Vml(m, l):
    integral, error = integrate.quad(lambda x: normed_func[m](x) * V(x) * normed_func[l](x), A, B, limit=5000)
    return integral


def check_potential():
    ratios = []
    for i in range(0, len(energies)):
        for j in range(i + 1, len(energies)):
            Vml = get_Vml(i, j)
            print("V[", i, ",", j, "] =", Vml)
            ratios.append(abs(Vml) / abs(energies[i] - energies[j]))

    print("Max ratio in check", max(ratios))


def compute_energy_for_perturbed_potential_via_formulas():
    global energies
    first_amendment = get_Vml(0, 0)
    print("first_amendment", first_amendment)
    first_energy = energies[0] + first_amendment
    print("First energy", first_energy)
    second_amendment = sum(
        abs(get_Vml(0, i)) ** 2 / (energies[0] - energies[i]) for i in range(1, len(energies)))
    print("second_amendment", second_amendment)
    print("Second energy", first_energy + second_amendment)
    return first_energy + second_amendment


def compute_func_seq_for_perturbed_potential_via_formulas():
    global energies, normed_func, X

    Vml = [get_Vml(i, 0) for i in range(0, len(energies))]

    def psi(x):
        inner_func = lambda x, i: Vml[i] / (energies[0] - energies[i]) * normed_func[i](x)
        summa = lambda x: sum(inner_func(x, i) for i in range(1, len(energies)))
        return normed_func[0](x) + summa(x)

    return [psi(x) for x in X]


def draw_plot(pert_energy, pert_func, energy, func, computed_energy, computed_func):
    # global energies, func_seq_list, normed_func_list
    # plt.axis([A, B, min(Psi), W])

    # normed_func = get_normed_func_by_sequence(get_function_seq_by_energy(energy, U))
    global X
    Upot = to_num(U)
    plt.plot(X, Upot, 'g-', linewidth=2.0, label="U(x)")
    Zero = np.zeros(n, dtype=float)
    plt.plot(X, Zero, 'k-', linewidth=1.0)  # abscissa axis
    plt.plot(X, func, linestyle="dashed", color="#CE0EE3", linewidth=1.0, label="Fi(x)")
    plt.plot(X, pert_func, 'b-', linewidth=1.0, label="Psi(x)")
    plt.plot(X, computed_func, 'r-', linewidth=1.0, label="Psi_computed(x)")
    plt.xlabel("X", fontsize=18, color="k")
    plt.ylabel("Psi(x), Fi(x), U(x)", fontsize=18, color="k")
    plt.grid(True)
    plt.legend(fontsize=16, shadow=True, fancybox=True, loc='upper right')
    plt.figtext(0.01, 0.9, "E (not perturbed) = " + format(energy, "10.7f"), fontsize=14, color='black')
    plt.figtext(0.01, 0.8, "E (direct) = " + format(pert_energy, "10.7f"), fontsize=14, color='black')
    plt.figtext(0.01, 0.7, "E (computed)    = " + format(computed_energy, "10.7f"), fontsize=14, color='black')
    plt.savefig("result.pdf", dpi=300)
    plt.show()


# def plotting_final_results(numFun, title):
#     guadFun = [i * i for i in numFun]
#     N = integralNum(guadFun)
#     NguadRoot = pow(N, 0.5)
#     normedFun = [i / NguadRoot for i in numFun]
#     probDensity = [i * i for i in normedFun]
#     # normedIntegral = integral(probDensity)
#     # print("Normed integral:", normedIntegral)
#     plt.axis([A, B, -1.0, 2.0])
#     plt.plot(X, normedFun, 'b-', linewidth=2.0, label="Psi'(x)")
#     plt.plot(X, probDensity, 'r-', linewidth=2.0, label="p(x)")
#     plt.xlabel("X", fontsize=18, color="k")
#     plt.ylabel("Psi'(x), p(x)", fontsize=18, color="k")
#     plt.grid(True)
#     plt.legend(fontsize=16, shadow=True, fancybox=True, loc='upper right')
#     string1 = "px = " + format(px(numFun), "7.3f")
#     string2 = "px^2 = " + format(px2(numFun), "7.3f")
#     plt.text(-4.0, 1.8, title, fontsize=14, color='black')
#     plt.text(-4.0, 1.6, string1, fontsize=14, color='black')
#     plt.text(-4.0, 1.4, string2, fontsize=14, color="black")
#     # save to file
#     name = "schrodinger-2b" + "-[" + title + "].pdf"
#     plt.savefig(name, dpi=300)
#     plt.show()

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

    # fmax = +10.0
    # fmin = -10.0
    # plot
    # plotting_f()
    # output of roots
    nroots = len(energy)
    print("nroots =", nroots)
    for i in np.arange(nroots):
        stroka = "i = {:1d}    energy[i] = {:12.5e}"
        print(stroka.format(i, energy[i]))

    # plotting_final_results(func[0], "Base state")
    # plotting_final_results(func[3], "3rd state")

    return energy, func


def to_num(func):
    global X
    return [func(x) for x in X]


def main():
    global energies, normed_func
    # plotting_u(U0)
    # plotting_u(U)

    # Counting energy and function for perturbed potential
    pert_energies, pert_func = count_energies(1, U)
    pert_normed_func = normalize_func(pert_func[0])
    print("Base energy for perturbed potential:", pert_energies[0])

    energies, func = count_energies(20, U0)
    normed_func = [normalize_func(num_fun) for num_fun in func]
    # energies = [0.13157468096494676, 0.15949321705102915, 0.51551614838361748, 1.1801860681319241, 1.4219754865670209,
    #             2.0787318099570271, 2.5224866503643986, 3.2763789475798601, 3.9198726021170618, 4.7911556995463371]
    print("Energies for not perturbed potential:", energies)
    check_potential()

    computed_base_energy = compute_energy_for_perturbed_potential_via_formulas()
    computed_func = compute_func_seq_for_perturbed_potential_via_formulas()
    print("Computed base energy", computed_base_energy)
    draw_plot(
        pert_energies[0], to_num(pert_normed_func),
        energies[0], to_num(normed_func[0]),
        computed_base_energy, computed_func)


main()
