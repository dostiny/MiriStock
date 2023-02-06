import './css/Create.css';
import { TextField, Button } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import { useState } from 'react';
import { communityAPI } from '../../api/api';

const Create = () => {
  const navigate = useNavigate();

  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');
  const ChangeTitle = (e) => {
    setTitle(e.target.value);
  };
  const ChangeContent = (e) => {
    setContent(e.target.value);
  };
  return (
    <div className="create-page">
      <div className="create-form">
        <div className="create-title">
          <TextField
            id="article-title"
            placeholder="제목을 입력하세요"
            inputProps={{
              style: {
                height: '20px',
                width: '250px',
              },
            }}
            onChange={ChangeTitle}
          />
        </div>
        <div className="create-content">
          <TextField
            id="article-content"
            placeholder="내용을 입력하세요."
            multiline
            rows={10}
            inputProps={{
              style: {
                height: '250px',
                width: '250px',
              },
            }}
            onChange={ChangeContent}
          />
        </div>
      </div>
      <div className="btn-form">
        <Button
          id="cancel-btn"
          variant="outlined"
          size="large"
          onClick={() => {
            navigate(-1);
          }}
        >
          취소
        </Button>

        <Button
          id="create-btn"
          variant="outlined"
          size="large"
          onClick={() => {
            communityAPI
              .createArticle(title, content)
              .then((request) => console.log(request.data))
              .catch((err) => console.log(err));
            //새로고침페이지
            window.location.replace('/community');
          }}
        >
          작성
        </Button>
      </div>
    </div>
  );
};

export default Create;
