…or create a new repository on the command line

echo "# android_utils" >> README.md

git init

git add README.md

#提交到本地
git commit -m "first commit"

git remote add origin https://github.com/lixingdeyongyuan/android_utils.git

#提交到远程服务器
git push -u origin master

…or push an existing repository from the command line

git remote add origin https://github.com/lixingdeyongyuan/android_utils.git

git push -u origin master

…or import code from another repository

You can initialize this repository with code from a Subversion, Mercurial, or TFS project.

0
1
3
4
5
