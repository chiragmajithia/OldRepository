from numpy import *
from pylab import *
import util
from matplotlib.pyplot import *

def pca(X, K):
    '''
    X is an N*D matrix of data (N points in D dimensions)
    K is the desired maximum target dimensionality (K <= min{N,D})

    should return a tuple (P, Z, evals)
    
    where P is the projected data (N*K) where
    the first dimension is the higest variance,
    the second dimension is the second higest variance, etc.

    Z is the projection matrix (D*K) that projects the data into
    the low dimensional space (i.e., P = X * Z).

    and evals, a K dimensional array of eigenvalues (sorted)
    '''
    
    N,D = X.shape
    # make sure we don't look for too many eigs!
    if K > N:
        K = N
    if K > D:
        K = D
    #centring data
    X_c = centerData(X)
    #creating square matrix
    X_s =  dot(X_c.T,X_c) / real(X_c.shape[0])
    #computing eigen vals and eigen vectors
    [evals,evecs] = linalg.eig(X_s)
    #removing the imageinary part
    evals = real(evals)
    evecs = real(evecs)
    #sorting the eigen vals in increasing order
    indx_arr = argsort(evals)
    #reversing the sorted indices -> max to least order
    indx_arr = indx_arr[::-1]
    #Projection Matrix D x K
    Z = evecs[:,indx_arr[0:K]]
    #Projected Data N x K dimension
    P = dot(X,Z)
    return (P,Z,evals[indx_arr[0 : K]])


def centerData(X):
    N,D = X.shape
    mean_N = X.mean(axis = 0)
    mean_matrix = array([mean_N,]*N)
    center_N = subtract(X,mean_matrix)
    return center_N


def runRandom():
    Si = util.sqrtm(array([[3,2],[2,4]]))
    r  = randn(1000000,2)
    x = dot(r, Si)
    plot(x[:,0], x[:,1], 'b.')
    show(False)
    (P,Z,evals) = pca(x, 2)
    P.reshape(P.shape[0],P.shape[1])
    x0 = dot(P[:,0].reshape(P.shape[0],1), Z[:,0].reshape(1,2))
    x1 = dot(P[:,1].reshape(P.shape[0],1), Z[:,1].reshape(1,2))
    plot(x[:,0], x[:,1], 'b.', x0[:,0], x0[:,1], 'r.', x1[:,0], x1[:,1], 'g.')
    axis('equal')
    show(False)
    print dot(x.T,x) / real(x.shape[0])
    return (P,Z,x)

def runData(X,d):
    close('all')
    (P,Z,evals) = pca(X, d)
    max_eval = sum(evals);
    norm_evals = divide(evals,max_eval)
    cum_sum = cumsum(norm_evals)
    indx_90 = find(cum_sum > 0.9)
    indx_95 = find(cum_sum > 0.95)
    print 'number of dmensions to include 90% of the variance' + str(indx_90[0]) + 'with cummulative some of '+str(cum_sum[indx_90[0]])
    print 'number of dmensions to include 95% of the variance' + str(indx_95[0]) + 'with cummulative some of '+str(cum_sum[indx_95[0]])

    fig, ax = plt.subplots(1, 1)
    ax = plot(norm_evals,'x',markersize = 10.0,linewidth=3.0,color='r')
    ax = plot(norm_evals,'bo',markersize = 10.0,linewidth=3.0,color='b')
    fig, ax = plt.subplots(1, 1)
    ax.grid(zorder=0)
    setPlot(ax,indx_90,indx_95,d)
    ax = ax.bar(range(1,len(cum_sum)+1), cum_sum, width=0.3, align='center', color='skyblue', zorder=3)
    
    util.drawDigits(Z.T[:d,:], arange(50))
    show(False)
    return (P,Z,norm_evals)

def setPlot(ax,indx_90,indx_95,d):
    major_ticks = np.arange(0, 1, 0.1)                                              
    minor_ticks = np.arange(0, 1, 0.05)                                               

    major_tickx = np.arange(0, d, 1)                                              
    minor_tickx = np.arange(0, d, 0.05)  
    ax.set_xticks(major_tickx)                                                       
    ax.set_xticks(minor_tickx, minor=True)                                           
    ax.set_yticks(major_ticks)                                                       
    ax.set_yticks(minor_ticks, minor=True)  
    #ax.grid(which='both')                                                            

    # or if you want differnet settings for the grids:                                          
    ax.grid(which='minor', alpha=0.5)                                                       
    ax.grid(which='major', alpha=1) 
