import './css/Charts.css';
import { useState, useEffect } from 'react';
// import { stockAPI } from '../api/api'; // api 통신

import mirilogo from '../static/mirilogo.png';
import { userStore, favoriteStore } from '../store';
import LineChart from '../components/chart/LineChart';
import Financial from '../components/chart/Financial';
import News from '../components/detail/News';
import { Button } from '@mui/material';
import { AiFillStar } from 'react-icons/ai';
import { useParams, useLocation, useNavigate } from 'react-router-dom';
import { memberAPI } from '../api/api';

const StockDetail = () => {
  const { user } = userStore((state) => state);
  const navigate = useNavigate();
  // 오늘 날짜
  let today = user.memberassetCurrentTime;
  today = String(today);
  let userDate =
    today.slice(0, 4) + '.' + today.slice(4, 6) + '.' + today.slice(6, 8);
  let { stockCode } = useParams();
  //관심주식관련로직
  const { favoriteStocks, setFavoriteStocks } = favoriteStore((state) => state);
  const [isFavorite, setIsFavorite] = useState();
  const [updateFavorite, setUpdateFavorite] = useState(0); //렌더링용
  useEffect(() => {
    const isFavorite = (stock) => {
      if (stock.stockCode === stockCode) {
        return true;
      }
    };
    const favoriteStock = favoriteStocks.filter(isFavorite);
    if (favoriteStock.length === 1) {
      setIsFavorite(true);
    } else {
      setIsFavorite(false);
    }
  }, []);
  useEffect(() => {
    //밥먹고와서 axios로 관심주식목록불러와서 리프레시하기.
    const getFavorite = async () => {
      await memberAPI
        .intersetStocks(user.memberassetCurrentTime)
        .then((request) => {
          setFavoriteStocks(request.data);
        })
        .catch((err) => console.log(err));
    };
    getFavorite();
  }, [updateFavorite]);
  const location = useLocation();
  // 날짜데이터를 시간으로 변환하는 함수
  const dayToTime = (date) => {
    let year, month, day, time;
    year = parseInt(date / 10000);
    month = parseInt((date - year * 10000) / 100) - 1;
    day = date - year * 10000 - (month + 1) * 100;
    time = new Date(year, month, day);
    // console.log(time.getTime());
    // console.log(year, month, day);
    return time.getTime();
  };
  // 오늘날짜 -> 종목명
  const [stockInfo, setStockInfo] = useState('');

  useEffect(() => {
    if (location.state) {
      setStockInfo(location.state.stockName);
    } else {
      navigate('/');
    }
  }, [location]);

  return (
    <div className="detail-container">
      <div className="detail-header">
        <strong
          onClick={() => {
            navigate(-1);
          }}
        >
          〈
        </strong>
        <div className="detaildate">{userDate}</div>
        <div>
          <img src={mirilogo} className="mirilogo" />
        </div>
      </div>
      <div className="detail-title">
        <b>{stockInfo}</b>
        <span>{stockCode}</span>
        <div className="detail-favorite-icon">
          <AiFillStar
            // 클릭시 관심주식 on / off
            size={40}
            style={isFavorite ? { color: '#FFCC00' } : { color: '#AAA7A7' }}
            onClick={() => {
              if (isFavorite === true) {
                memberAPI
                  .deleteIntersetStocks(stockCode)
                  .then((request) => {
                    setUpdateFavorite(updateFavorite + 1);
                    setIsFavorite(false);
                  })
                  .catch((err) => console.log(err));
              } else {
                memberAPI
                  .addIntersetStocks(stockCode)
                  .then((request) => {
                    setIsFavorite(true);
                    setUpdateFavorite(updateFavorite + 1);
                  })
                  .catch((err) => console.log(err));
              }
            }}
          />
        </div>
      </div>
      <LineChart
        stockCode={stockCode}
        today={today}
        dayToTime={dayToTime}
        setStockInfo={setStockInfo}
      />

      <div className="space-between space-margin">
        <Button
          style={{ width: '48%' }}
          variant="contained"
          color="primary"
          disableElevation
          onClick={() => {
            navigate(`buyStock`, {
              state: {
                stockCode: stockCode,
              },
            });
          }}
        >
          매수
        </Button>
        <Button
          style={{ width: '48%' }}
          variant="contained"
          color="primary"
          disableElevation
          onClick={() => {
            navigate(`sellStock`, {
              state: {
                stockCode: stockCode,
              },
            });
          }}
        >
          매도
        </Button>
      </div>
      <div className="space-margin divbox">
        <div className="charts-title">주요 뉴스</div>
        <div className="charts-content" style={{ height: '190px' }}>
          <News />
        </div>
      </div>
      <div className="space-margin divbox">
        <div className="div-title">재무 제표</div>
        <div>
          <Financial today={today} />
        </div>
      </div>
    </div>
  );
};

export default StockDetail;
