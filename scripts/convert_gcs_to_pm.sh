#!/usr/bin/env bash

# 위도,경도 -> PM 좌표 변환

# validate
if [ "$#" -lt "2" ]; then
  echo "Usage: convert_gcs_to_pm.sh {LATITUDE} {LONGITUDE}"
  exit 1
fi


# resolve parameters
LATITUDE=$1
LONGITUDE=$2


# print
echo "LATITUDE: ${LATITUDE}"
echo "LONGITUDE: ${LONGITUDE}"


# execute
curl -s 'http://map.ngii.go.kr/common/proxy.do' -H 'Accept: application/xml, text/xml, */*; q=0.01' -H 'Origin: http://map.ngii.go.kr' -H 'X-Requested-With: XMLHttpRequest' -H 'User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.88 Safari/537.36' -H 'Content-Type: application/x-www-form-urlencoded; charset=UTF-8' -H 'Referer: http://map.ngii.go.kr/ms/mesrInfo/coordinate.do' -H 'Accept-Encoding: gzip, deflate' --data 'url=http%3A%2F%2Fnbns.ngii.go.kr%2Frest%2Fsearch%2FcoordConv.do&srcCRS=LLG&dstCRS=utmk&srcELP=GRS80&dstELP=GRS80&pos='${LATITUDE}'%2520'${LONGITUDE}'' --compressed --insecure
