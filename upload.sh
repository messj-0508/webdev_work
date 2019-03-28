msg=$1  # $1为第一个参数
git add .
git commit -m"${msg}"
git push
git status
