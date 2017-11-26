"""
One-dimensional Schrödinger steady-state equation.
Square potential well.
"""
import numpy as np
from scipy.integrate import odeint
from scipy.misc import derivative
from scipy.interpolate import interp1d
from scipy.special import genlaguerre
import matplotlib.pyplot as plt
import scipy.integrate as integrate
global r, n, Psi, Fi, X, XX

LST = open("schrodinger-2b.txt", "wt")
lagfun = genlaguerre(10, 3.5)

# potential function
def U(x):
    return v0 * lagfun(abs(x)) if abs(x) < L else W
    # return v0 * lagfun(abs(x))

# function (13)
def q(e, x):
    return 2.0*(e-U(x))


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

def plotting_u():
    plt.axis([A, B, U0, W])
    Upot = np.array([U(X[i]) for i in np.arange(n)])
    plt.plot(X, Upot, 'g-', linewidth=2.0, label="U(x)")
    plt.show()

def plotting_f():
    plt.axis([U0, e2, fmin, fmax])
    ZeroE = np.zeros(ne, dtype=float)
    plt.plot(ee, ZeroE, 'k-', linewidth=1.0)  # abscissa axis
    plt.plot(ee, af, 'bo', markersize=1)
    plt.xlabel("E", fontsize=18, color="k")
    plt.ylabel("f(E)", fontsize=18, color="k")
    plt.grid(True)
    # save to file
    plt.savefig('schrodinger-2b-f.pdf', dpi=300)
    plt.show()


def plotting_wf(e):
    global r, n, Psi, Fi, X, XX
    ff = f_fun(e)
    plt.axis([A, B, -3.0, W])
    Upot = np.array([U(X[i]) for i in np.arange(n)])
    plt.plot(X, Upot, 'g-', linewidth=2.0, label="U(x)")
    Zero = np.zeros(n, dtype=float)
    plt.plot(X, Zero, 'k-', linewidth=1.0)  # abscissa axis
    plt.plot(X, Psi, 'r-', linewidth=2.0, label="Psi(x)")
    plt.plot(XX, Fi, 'b-', linewidth=2.0, label="Fi(x)")
    plt.xlabel("X", fontsize=18, color="k")
    plt.ylabel("Psi(x), Fi(x), U(x)", fontsize=18, color="k")
    plt.grid(True)
    plt.legend(fontsize=16, shadow=True, fancybox=True, loc='upper right')
    plt.plot([X[r]], [Psi[r]], color='red', marker='o', markersize=7)
    string1 = "E    = " + format(e, "10.7f")
    string2 = "f(E) = " + format(ff, "10.3e")
    plt.text(-4.0, 2.7, string1, fontsize=14, color='black')
    plt.text(-4.0, 2.3, string2, fontsize=14, color="black")
    # save to file
    name = "schrodinger-2b" + "-" + str(ngr) + ".pdf"
    plt.savefig(name, dpi=300)
    plt.show()


def integralNum(numFun):
    curve = interp1d(X, numFun, kind='cubic', bounds_error=False, fill_value="extrapolate")
    return integralCurve(curve)

def integralCurve(curve):
    result = integrate.quad(curve, A, B)
    if result[1] > 1.0e-6:
        print("Warning! Not enough accuracy for integral calculation:", result[1])
    return result[0]

def px(numFun):
    curve = interp1d(X, numFun, kind='cubic', bounds_error=False, fill_value="extrapolate")
    derNum = [derivative(curve, x, dx=1.e-6) for x in X]
    derCurve = interp1d(X, derNum, kind='cubic', bounds_error=False, fill_value="extrapolate")
    integralFun = lambda x: curve(x) * derCurve(x)
    integralVal = integralCurve(integralFun)
    return complex(0, -integralVal)

def px2(numFun):
    curve = interp1d(X, numFun, kind='cubic', bounds_error=False, fill_value="extrapolate")
    derNum = [derivative(curve, x, dx=1.e-6, n=2) for x in X]
    derCurve = interp1d(X, derNum, kind='cubic', bounds_error=False, fill_value="extrapolate")
    integralFun = lambda x: curve(x) * derCurve(x)
    integralVal = integralCurve(integralFun)
    return -integralVal


def plotting_final_results(numFun, title):
    guadFun = [i * i for i in numFun]
    N = integralNum(guadFun)
    NguadRoot = pow(N, 0.5)
    normedFun = [i / NguadRoot for i in numFun]
    probDensity = [i * i for i in normedFun]
    # normedIntegral = integral(probDensity)
    # print("Normed integral:", normedIntegral)
    plt.axis([A, B, -1.0, 2.0])
    plt.plot(X, normedFun, 'b-', linewidth=2.0, label="Psi'(x)")
    plt.plot(X, probDensity, 'r-', linewidth=2.0, label="p(x)")
    plt.xlabel("X", fontsize=18, color="k")
    plt.ylabel("Psi'(x), p(x)", fontsize=18, color="k")
    plt.grid(True)
    plt.legend(fontsize=16, shadow=True, fancybox=True, loc='upper right')
    string1 = "px = " + format(px(numFun), "7.3f")
    string2 = "px^2 = " + format(px2(numFun), "7.3f")  # todo
    plt.text(-4.0, 1.8, title, fontsize=14, color='black')
    plt.text(-4.0, 1.6, string1, fontsize=14, color='black')
    plt.text(-4.0, 1.4, string2, fontsize=14, color="black")
    # save to file
    name = "schrodinger-2b" + "-[" + title + "].pdf"
    plt.savefig(name, dpi=300)
    plt.show()


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
print("n=", n, file=LST)
# minimum of potential (atomic units) - for visualization only!
U0 = -0.2
# U0 = -2.0
# maximum of potential (atomic units) - for visualization only!
W = 3.0
# x-coordinates of the nodes
X  = np.linspace(A, B, n)  # forward
XX = np.linspace(B, A, n)  # backwards
# node of sewing
r = (n-1)*3//4      # forward
rr = n-r-1          # backwards
print("r=", r)
print("r=", r, file=LST)
print("rr=", rr)
print("rr=", rr, file=LST)
print("X[r]=", X[r])
print("X[r]=", X[r], file=LST)
print("XX[rr]=", XX[rr])
print("XX[rr]=", XX[rr], file=LST)
# plot of f(e)
e1 = U0+0.0005
e2 = 1.0
# e2 = 5.0
print("e1=", e1, "   e2=", e2)
print("e1=", e1, "   e2=", e2, file=LST)
ne = 151
# ne = 501
print("ne=", ne)
print("ne=", ne, file=LST)
ee = np.linspace(e1, e2, ne)
af = np.zeros(ne, dtype=float)
porog = 5.0
tol = 1.0e-7
energy = []
func = []
ngr = 0
# plotting_u()
for i in np.arange(ne):
    e = ee[i]
    af[i] = f_fun(e)
    stroka = "i = {:3d}   e = {:8.5f}  f[e] = {:12.5e}"
    print(stroka.format(i, e, af[i]))
    print(stroka.format(i, e, af[i]), file=LST)
    if i > 0:
        Log1 = af[i]*af[i-1] < 0.0
        Log2 = np.abs(af[i]-af[i-1]) < porog
        if Log1 and Log2:
            energy1 = ee[i-1]
            energy2 = ee[i]
            eval = m_bis(energy1, energy2, tol)
            print("eval = {:12.5e}".format(eval))
            dummy = plotting_wf(eval)
            energy.append(eval)
            func.append(Psi)
            ngr += 1

fmax = +10.0
fmin = -10.0
# plot
dummy = plotting_f()
# output of roots
nroots = len(energy)
print("nroots =", nroots)
print("nroots =", nroots, file=LST)
for i in np.arange(nroots):
    stroka = "i = {:1d}    energy[i] = {:12.5e}"
    print(stroka.format(i, energy[i]))
    print(stroka.format(i, energy[i]), file=LST)

plotting_final_results(func[0], "Base state")
plotting_final_results(func[3], "3rd state")
