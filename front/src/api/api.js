import axios from 'axios';

const BASE_URL = 'http://192.168.31.160';
const token =
  'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0a2RndXNkbDYzQG5hdmVyLmNvbSIsInJvbGUiOiJNRU1CRVIiLCJleHAiOjE2NzUzMTY2MDZ9.yE2kwDnuQvR4B9ypwHElsJ9jngDk-aeIrf-rxwnMnQw';
const api = axios.create({
  baseURL: BASE_URL,
  headers: {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  },
});

export default api;

export const memberAPI = {
  test: () => api.get(`/login/oauth2/code/kakao`),
};

export const rankAPI = {
  increase: () => api.get(`/stockdata/rate/increase/${20210112}`),
  decrease: () => api.get(`/stockdata/rate/decrease/${20210112}`),
  capital: () => api.get(`/stockdata/amount/top`), //백에서 미완성
};

export const stockAPI = {
  stockDetail: (stockCode, startDate, endDate) =>
    api.get(`stockdata/detail`, {
      params: { stockCode: stockCode, startDate: startDate, endDate: endDate },
    }),
};

export const searchAPI = {
  serachStock: (stock) =>
    api.get(`stock/search`, { params: { stockName: stock } }),
};
