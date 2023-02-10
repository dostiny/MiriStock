import Spinner from '../static/Spinner-1s-217px.gif';

export const Loading = () => {
  return (
    <div
      style={{
        position: 'absolute',
        width: '100vw',
        height: '100vh',
        top: '0',
        left: '0',
        background: '#ffffffb7',
        zIndex: '999',
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
        justifyContent: 'center',
      }}
    >
      <div style={{ textAlign: 'center' }}>
        Loading <img src={Spinner} alt="로딩중" />
      </div>
    </div>
  );
};

export default Loading;
