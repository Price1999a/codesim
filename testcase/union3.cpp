#include<iostream>
#include<vector>

using namespace std;

#define MAXN 1001
class UF{
public:
    int fa[MAXN],rank[MAXN];
    UF(int n){
        for(int i=1;i<=n;i++){
            fa[i]=i;
            rank[i]=1;
        }
    }
    int find(int x){
        return x==fa[x]?x:(fa[x]=find(fa[x]));
    }
    void uni(int i,int j){
        int x=find(i),y=find(j);
        if(rank[x]<=rank[y])fa[x]=y;
        else fa[y]=x;
        if(rank[x]==rank[y]&&x!=y)rank[y]++;
    }
};
class Solution {
public:
    vector<int> findRedundantDirectedConnection(vector<vector<int>>& edges) {
        int n=edges.size();
        vector<int>in(n+1,0);
        int target=-1;
        for(int i=0;i<n;i++){
            in[edges[i][1]]++;
            if(in[edges[i][1]]==2){
                target=edges[i][1];
                break;
            }
        }
        if(target==-1){
            UF uf(n);
            for(int i=0;i<n;i++){
                int x=uf.find(edges[i][0]),y=uf.find(edges[i][1]);
                if(x==y)return edges[i];
                uf.uni(x,y);
            }
        }
        else {
            vector<vector<int>>twoEdges;
            UF uf(n);
            for(int i=0;i<n;i++){
                if(edges[i][1]==target){
                    twoEdges.push_back({edges[i][0],edges[i][1]});
                }else {
                    uf.uni(edges[i][0],edges[i][1]);
                }
            }
            //assert(twoEdges.size()==2);
            if(uf.find(twoEdges[0][0])!=uf.find(twoEdges[0][1]))return twoEdges[1];
            if(uf.find(twoEdges[1][0])!=uf.find(twoEdges[1][1]))return twoEdges[0];
            return twoEdges[1];
        }
        return {};
    }
};
