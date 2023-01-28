# 11팀

---

---

23.01.26

1. npm i zustand
2. store.js 생성
3. import { create } from ‘zustand’;

<img src="file:///C:/Users/SSAFY/AppData/Roaming/marktext/images/2023-01-26-13-57-18-image.png" title="" alt="" data-align="center">

- state를 create한다. 타 상태관리 라이브러리와는 다르게 이게 끝임
- bears → 변수명
- increasePopulation → state 상태를 변경시킬 함수
- set은 보통 두가지 방법으로 사용함

```jsx
set({ bears : 0 });
set(state => ({ bears : 0 }));
```

우리는 무조건 아래방식으로 set쓸예정

## 단일 변수들은 위와같은 방법으로 변경하면 됨

## 하지만 우리가 받는 데이터들은 대부분 어레이속 오브젝트 형식일거라 생각

## 위와 같은 방식으로는 단일개체값만 변경이 됨

```js
cart : [
    {id : 0, name : 'White and Black', count : 2},
    {id : 1, name : 'Grey Yordan', count : 1}
],
```

# 하지만 cart배열안에 첫번째 오브젝트의 count값을 바꾸고 싶다면?

![](C:\Users\SSAFY\AppData\Roaming\marktext\images\2023-01-26-13-59-58-image.png)

- 먼저 testStore 안에 상태변경 함수를 만든다. 이 방법은 위에서 했던것과 동일하다.

```jsx
set 안이 좀 다른데
({ cart : state.cart[id].count + 1 }) 이런 형식이 아니라
({ cart : increaseCounts(state.cart, id) }) 이런 형식으로 함수를 호출해서 인자를 넘겨준다.
```

이때 호출한 함수는 testStore 바깥에 별도로 존재한다.

![](C:\Users\SSAFY\AppData\Roaming\marktext\images\2023-01-26-14-00-30-image.png)

- 여기서부턴 순수 자바스크립트 문법의 영역이라 좋다.
- 마지막에 return으로 인자로 받았던 값만 반환을 해주면 된다.

마지막으로 export 는

```js
export {useStore, testStore}
```

이런식으로 하고

App.js 에서 사용을 예시로 보여주겠다.

```js
import {useStore, testStore} from './store.js'
```

맨 윗단에 import를 이런형식으로 해오고

```js
const { bears, increasePopulation, removeAllBears } = useStore(state => state)
const { cart, increaseCount, decreaseCount } = testStore(state => state)
```

사용할 컴포넌트함수 안에 이렇게 불러온다.

![](C:\Users\SSAFY\AppData\Roaming\marktext\images\2023-01-26-14-03-58-image.png)

이런식으로 사용하면 끝!

메모 : 온클릭이벤트에 ; 으로 여러개의 함수 넣을수있음.

# 값들을 로컬스토리지에 저장해보자!

store.js 에 persist를 import해준다.

zustand middleware에서 자체적으로 제공해줘서 별도의 라이브러리 설치는 필요없다.

```js
import { persist } from "zustand/middleware"; // localStorage
```

그리고 create(set ⇒ 원래 이부분이었던것을

persist()로 크게 둘러준뒤

스토리지 이름만 설정하면 끝이다.

간단!

![](C:\Users\SSAFY\AppData\Roaming\marktext\images\2023-01-26-14-04-49-image.png)



---

---

23.01.25 - 학습

> 상세페이지에 상품명 넣어보자

임시 글자들만 들어있으면 밋밋해서 그렇습니다.

그래서 shoes 라는 state에 있던 상품정보들을 Detail 컴포넌트에 꽂아넣어봅시다.

근데 안타깝게도 shoes는 App 컴포넌트에 있으니 App -> Detail 이렇게 전송하면 쓸 수 있겠군요.

```jsx
<Route path="/detail" element={ <Detail shoes={shoes}/> }/>
```

그래서 App.js 안에 <Detail> 쓰는 곳에서 일단 props 전송하고

```jsx
(Detail.js)
<div className="container>
  <div className="row">
    <div className="col-md-6">
      <img src="<https://codingapple1.github.io/shop/shoes1.jpg>" width="100%" />
    </div>
    <div className="col-md-6 mt-4">
      <h4 className="pt-5">{props.shoes[0].title}</h4>
      <p>{props.shoes[0].content}</p>
      <p>{props.shoes[0].price}원</p>
      <button className="btn btn-danger">주문하기</button>
    </div>
  </div>
</div>
```

Detail 컴포넌트는 props 파라미터 등록해서 shoes를 자유롭게 사용했습니다.

props.shoes[0].title 하면 0번째 상품명 나올듯

**Q. 근데 shoes라는 state를 Detail.js 안에서 또 만들면 굳이 props 필요없지 않나요?**

A. 나중에 수정이 필요하면 두군데 수정해야해서 귀찮으니 그러면 안됩니다.

> 상세페이지 여러개 만들려면

방금 만든건 0번 상품의 상세페이지일 뿐입니다.

상품이 3개니까 상세페이지도 3개 필요할텐데

그럼 이렇게 코드짜면 되겠군요.

<Route> 쓰면 페이지하나 만들 수 있다고 했으니까...

```jsx
<Route path="/detail/0" element={ <Detail shoes={shoes}/> }/>
<Route path="/detail/1" element={ <Detail shoes={shoes}/> }/>
<Route path="/detail/2" element={ <Detail shoes={shoes}/> }/>
```

<Route>를 3개 만드는겁니다. 그럼 페이지 3개 완성

path 작명시 슬래시 기호도 맘대로 사용가능한데 단어간 띄어쓰기용으로 많이 사용합니다.

근데 상품이 100만개라면 <Route>도 100만개 만들것입니까?

그건 너무 끔찍하기 때문에 다른 방법을 사용합니다.

```jsx
<Route path="/detail/:id" element={ <Detail shoes={shoes}/> }/>
```

페이지를 여러개 만들고 싶으면 URL 파라미터라는 문법을 사용가능합니다.

path 작명할 때 /:어쩌구 이렇게 사용하면 **"아무 문자"**를 뜻합니다.

그래서 위의 <Route>는 누군가 주소창에 **/detail/아무거나** 입력했을 때

<Detail> 컴포넌트 보여달라는 뜻입니다.

이제 그럼

**/detail/0**

**/detail/1**

**/detail/2**

이렇게 접속해도 <Detail> 컴포넌트 잘 보여줄 수 있습니다.

문제해결

> 페이지마다 똑같은 내용은 보여주기 싫은데요

/detail/0

/detail/1

/detail/2

이렇게 페이지는 여러개 만들어놨지만 접속해보면 다 똑같은 0번째 상품명만 보여주고 있습니다.

왜냐면 0번째 상품명 보여달라고 여러분이 코드짰으니까요.

이게 싫으면 이렇게 코드짤 수 있지않을까요.

```jsx
(Detail.js)

<h4 className="pt-5">{props.shoes[현재url에입력된숫자].title}</h4>
<p>{props.shoes[0].content}</p>
<p>{props.shoes[0].price}원</p>
<button className="btn btn-danger">주문하기</button>
```

0이라고 하드코딩해놨던 자리에

**현재url파라미터에 입력된숫자**를 넣는겁니다.

그럼 /detail/1로 접속하면 1번째 상품명을 보여줄 수 있을듯요.

저런 숫자를 가져올 수 있냐고요?

가져올 수 있습니다.

```jsx
import { useParams } from 'react-router-dom'

function Detail(){
  let {id} = useParams();
  console.log(id)

  return (
    <div className="container>
      <div className="row">
        <div className="col-md-6">
          <img src="<https://codingapple1.github.io/shop/shoes1.jpg>" width="100%" />
        </div>
        <div className="col-md-6 mt-4">
        <h4 className="pt-5">{props.shoes[id].title}</h4>
        <p>{props.shoes[0].content}</p>
        <p>{props.shoes[0].price}원</p>
        <button className="btn btn-danger">주문하기</button>
      </div>
    </div>
  </div>
  )
}
```

useParams() 라는 함수를 상단에서 import 해오면 쓸 수 있는데

이거 쓰면 현재 **/:url파라미터** 자리에 유저가 입력한 값을 가져올 수 있습니다.

변수에 저장해서 쓰든가 하면 됩니다.

그래서 위처럼 사용하면

누가 /detail/1로 접속하면 id라는 변수에 1이 들어옵니다.

누가 /detail/2로 접속하면 id라는 변수에 2가 들어옵니다.

그래서 props.shoes[id].title 이러면 아까 의도했던 기능이 완성되겠군요.

페이지마다 각각 다른 상품명이 보입니다.

(참고)

path 작명시 url 파라미터는 몇번이고 사용가능합니다. detail/:어쩌구/:저쩌구 이런식

---

---

23.01.20

> html 긴 부분 컴포넌트로 만들어보기

<div className="col-md-4"> 부분이 길고 복잡하고 자주 등장해서 컴포넌트로 만들어볼 것입니다.

다른데서 사용할 일이 없으면 굳이 컴포넌트화 하는게 필요없을 것 같은데

연습삼아서 아무튼 해봅시다.

```
function App(){
  return (
    <div className="App">
      (생략)
      <div className="container">
        <div className="row">
          <Card/>
          <Card/>
          <Card/>
        </div>
      </div>
    </div>
  )
}

function Card(){
  return (
    <div className="col-md-4">
      <img src="<https://codingapple1.github.io/shop/shoes1.jpg>" width="80%" />
      <h4>상품명</h4>
      <p>상품정보</p>
    </div>
  )
}
```

그래서 밑에 Card 라는 컴포넌트를 만들고 거기에 축약할 html을 담았습니다.

> shoes에 있던거 Card 컴포넌트안에 꽂아넣기

shoes라는 state는 App 컴포넌트에 있습니다.

그걸 App의 자식인 Card 컴포넌트가 쓰고 싶으면

App -> Card 이렇게 props 전송해주면 된다고 했습니다.

```
(function App 내부)

<Card shoes={shoes}/>
<Card shoes={shoes}/>
<Card shoes={shoes}/>
```

이렇게 보내고

```
function Card(props){
  return (
    <div className="col-md-4">
      <img src="<https://codingapple1.github.io/shop/shoes1.jpg>" width="80%" />
      <h4>{ props.shoes[0].title }</h4>
      <p>{ props.shoes[0].price }</p>
    </div>
  )
}
```

props.보낸거 쓰면 됩니다.

그럼 부모에 있던 shoes라는 state 사용가능합니다.

근데 잘보면 똑같은 0번 상품만 3개나 보이고 있습니다.

**Q. 왜 똑같은 카드만 3개 보이죠?**

컴퓨터는 여러분이 명령한대로 움직일 뿐입니다. 다른거 보여달라고 명령주면 될듯

> 컴포넌트를 매번 다르게 보여주고 싶으면

예전에 모달창 만들었을 때

**"props 활용하면 매번 다른 내용의 모달창 맘대로 만들 수 있다"**고 하지 않았습니까.

그래서 props 이용하면 됩니다.

<Card> 쓸 때 마다 각각 다른 정보를 props로 보내면 됩니다.

```
(function App 내부)

<Card shoes={shoes[0]}/>
<Card shoes={shoes[1]}/>
<Card shoes={shoes[2]}/>
```

이렇게 보내면

첫 째 <Card> 에는 {상품0 정보}

둘 째 <Card> 에는 {상품1 정보} 만 보낼 수 있겠군요.

```
function Card(props){
  return (
    <div className="col-md-4">
      <img src="<https://codingapple1.github.io/shop/shoes1.jpg>" width="80%" />
      <h4>{ props.shoes.title }</h4>
      <p>{ props.shoes.price }</p>
    </div>
  )
}
```

{상품0 정보} 이런 object자료를 props로 보냈으니까

데이터바인딩하는 문법도 약간 달라지겠네요.

이렇게 하면 <Card> 쓸 때마다 각각 다른 내용의 <Card>를 보여줄 수 있지 않을까요.

이거 말고도 방법은 여러가지가 있을 수 있겠군요.

**Q. 상품 사진이 3개 다 똑같이 보이는데 뭐가 문제인건가요?**

여러분이 shoes1.jpg만 보여달라고 코드 짜놔서 그런거지 컴퓨터는 죄가 없습니다.

props 활용하면 매번 다른 내용의 <Card> 보여줄 수 있다고 했으니 그거 활용해보면 됩니다.

```
(function App 내부)

<Card shoes={shoes[0]} i={1} />
<Card shoes={shoes[1]} i={2} />
<Card shoes={shoes[2]} i={3} />
```

이렇게 props로 숫자를 보내는겁니다. 대충 i라는 이름으로 보냈습니다.

```
function Card(props){
  return (
    <div className="col-md-4">
      <img src={'<https://codingapple1.github.io/shop/shoes>' + props.i + '.jpg'} width="80%" />
      <h4>{ props.shoes.title }</h4>
      <p>{ props.shoes.price }</p>
    </div>
  )
}
```

그럼 props.i 라고 출력하면 보냈던 0, 1, 2 이렇게 보냈던 숫자가 나옵니다.

그걸 shoes0.jpg 자리에 집어넣는겁니다.

그럼 매번 다른 신발이미지를 보여줄 수 있겠군요.

문자 중간에 변수넣고 싶으면 **'문자' + 변수 + '문자'** 이렇게 쓰면 됩니다.

---

---

23.01.19

1. 오늘 만든 상품목록을 컴포넌트로 만들어봅시다. 컴포넌트도 길면 다른 파일로 빼도 상관없음

2. 컴포넌트만들면 그 안에 데이터바인딩도 아마 다시해야겠군요

3. 반복적인 html이나 컴포넌트를 발견하면 연습삼아 map 반복문을 써봅시다.

여태까지 배웠던거 복습이라 새로운거 없습니다 혼자 힘으로 하도록 합시다.

> 오늘 필요한 상품데이터

```jsx
[
  {
    id : 0,
    title : "White and Black",
    content : "Born in France",
    price : 120000
  },

  {
    id : 1,
    title : "Red Knit",
    content : "Born in Seoul",
    price : 110000
  },

  {
    id : 2,
    title : "Grey Yordan",
    content : "Born in the States",
    price : 130000
  }
]
```

어려워 보이지만 별거 아닌데

[array자료]에 상품정보가 3개 들어있을 뿐입니다.

근데 상품정보가 너무 길고 복잡해서 {object자료} 에 넣어뒀을 뿐입니다.

그래서 축약하면 [ { }, { }, { } ] 이렇게 생김

**object 자료가 무엇임**

> export default / import 문법

근데 위에 있던 상품정보들을 state로 만들고 싶은데 useState() 안에 넣기엔 너무 깁니다.

그럴 땐 다른파일에 보관했다가 import해올 수도 있습니다.

예를 들어서 data.js라는 파일이 있는데 거기 있던 변수를 App.js 에서 가져와서 쓰고 싶으면

```jsx
(data.js 파일)

let a = 10;
export default a;
```

**export default 변수명;** 이렇게 쓰면 원하는 변수를 밖으로 내보낼 수 있습니다.

```jsx
(App.js 파일)

import a from './data.js';
console.log(a)
```

export 했던 변수를 다른 파일에서 사용하고 싶으면

**import 작명 from './파일경로'** 하면 됩니다.

위 코드에선 a 출력해보면 진짜 10 나옴

(유의점)

- 변수, 함수, 자료형 전부 export 가능합니다.
- 파일마다 export default 라는 키워드는 하나만 사용가능합니다.
- 파일경로는 ./ 부터 시작해야합니다. 현재경로라는 뜻임

> export { } / import { } 문법

여러개의 변수들을 내보내고싶으면 export default 말고 이런 문법을 씁니다.

```jsx
(data.js 파일)

var name1 = 'Kim';
var name2 = 'Park';
export { name1, name2 }
```

그럼 원하는 변수, 함수 등을 여러개 내보낼 수 있습니다.

```jsx
(App.js 파일)

import { name1, name2 } from './data.js';
```

export {} 이걸로 내보냈으면 가져다가 쓸 때 import {} 문법을 씁니다.

(유의점)

- export { } 했던 것들은 import { } 쓸 때 자유작명이 불가능합니다. export 했던 변수명 그대로 적어야함

그래서 결론은

export default / import 쓰거나

export { } / import { } 쓰거나 둘 중 마음에드는걸 써봅시다.

> 상품데이터도 다른 파일에 저장해두자

배웠으니까 활용해봐야합니다.

위에 있던 길고 복잡한 state를 다른 js파일에 저장해둔 뒤에

그걸 import 해와서 App.js의 useState 안에 넣어봅시다.

- **어떻게 했냐면**
  
  ```
  (data.js 파일)
  
  let data = 위에있던 긴 array 자료;
  export default data
  ```
  
  ```
  (App.js 파일)
  
  import data from './data.js';
  
  function App(){
    let [shoes] = useState(data);
  
  }
  ```
  
  이런 식으로 썼습니다.
  
  data.js 파일은 App.js와 같은 경로에 만들어서 경로는 그냥 ./data.js 인듯요

> 상품데이터를 html에 데이터바인딩하기

여러분이 방금 import 해온건 상품 3개의 데이터입니다.

각각 상품의 제목, 설명, 가격 이런 것들이 들어가있습니다.

```jsx
function App(){
  let [shoes] = useState(data);
  return (
    <div className="App">
      (생략)
      <div className="container">
        <div className="row">
          <div className="col-md-4">
            <img src="<https://codingapple1.github.io/shop/shoes1.jpg>" width="80%" />
            <h4>{ shoes[0].title }</h4>
            <p>{ shoes[0].price }</p>
          </div>
          <div className="col-md-4">
            <img src="<https://codingapple1.github.io/shop/shoes2.jpg>" width="80%" />
            <h4>상품명</h4>
            <p>상품정보</p>
          </div>
          <div className="col-md-4">
            <img src="<https://codingapple1.github.io/shop/shoes3.jpg>" width="80%" />
            <h4>상품명</h4>
            <p>상품정보</p>
          </div>
        </div>
      </div>
    </div>
  )
}
```

---

---

23.01.18

리액트는 원래 좀 자유롭습니다.

이미지 넣는 법도 서너개 있습니다.

**강의에서 사용하는 신발 이미지 URL**

> 대문만들기

<div className="main-bg"></div> 하나 넣고 css 파일에서 디자인넣으면 될 것 같은데

```
(App.css)

.main-bg {
  height : 300px;
  background-image : url('./bg.png');
  background-size : cover;
  background-position : center;
}
```

이렇게 디자인해봤습니다.

css 파일에서 src 폴더안에 있는 사진을 사용하고 싶으면

**./이미지경로**

사용하면 됩니다.

> html 안에서 src 폴더의 이미지를 넣고 싶으면

html 안에서 이미지를 집어넣고 싶으면

**이미지를 import 해오고 사용해야**합니다.

```jsx
import bg from './bg.png'

function App(){
  return (
    <div>
      <div className="main-bg" style={{ backgroundImage : 'url(' + bg + ')' }}></div>
    </div>
  )
}
```

1. import 작명 from './이미지경로' 한 다음에

2. 이미지경로가 필요한 곳에서 작명한걸 사용하면 됩니다.

<img>태그 쓰고싶으면 <img src={bg}/> 이렇게 써도 보입니다.

귀찮으면 css파일을 활용합시다.

> 화면을 가로로 3등분하고 싶으면

Bootstrap쓰면 레이아웃 짜는게 약간 간편해집니다.

가로로 화면을 3등분하고 싶으면

```jsx
<div className="container">
  <div className="row">
    <div className="col-md-4">안녕</div>
    <div className="col-md-4">안녕</div>
    <div className="col-md-4">안녕</div>
  </div>
</div>
```

이거 복사붙여넣기하면 됩니다.

심지어 모바일 사이즈에선 알아서 세로로 정렬해줍니다.

물론 CSS 잘하면 직접 하는게 더 효율적임

실은 React-bootstrap 사이트에서 Row 아니면 Grid 라고 검색해서 그거 복붙해야하는데

저건 원조 Bootstrap 사이트에서 복붙했습니다.

import 하기 귀찮아서 그랬습니다.

그래서 쇼핑몰스럽게 상품을 3개 진열해봤습니다.

```
function App(){
  return (
    <div className="App">
      (Navbar와 대문은 생략)

      <div className="container">
        <div className="row">
          <div className="col-md-4">
            <img src="<https://codingapple1.github.io/shop/shoes1.jpg>" width="80%" />
            <h4>상품명</h4>
            <p>상품정보</p>
          </div>
          <div className="col-md-4">
            <img src="<https://codingapple1.github.io/shop/shoes2.jpg>" width="80%" />
            <h4>상품명</h4>
            <p>상품정보</p>
          </div>
          <div className="col-md-4">
            <img src="<https://codingapple1.github.io/shop/shoes3.jpg>" width="80%" />
            <h4>상품명</h4>
            <p>상품정보</p>
          </div>
        </div>
      </div>
    </div>
  )
}
```

어디 호스팅되어있는 외부 이미지는 절대경로 그대로 작성하면 잘보입니다.

> public 폴더의 용도

여러가지 소스코드는 src 폴더에 보관하면 되는데

이미지같은 static 파일의 경우 public 폴더에 보관해도 됩니다.

리액트로 개발을 끝내면 build 작업이라는걸 하는데

지금까지 짰던 코드를 한 파일로 압축해주는 작업입니다.

src 폴더에 있던 코드와 파일은 다 압축이 되는데 public 폴더에 있는 것들은 그대로 보존해줍니다.

그래서 형태를 보존하고 싶은 파일은 public 폴더에 넣으면 되는데 js 파일은 그럴 일은 거의 없고

이미지, txt, json 등 수정이 필요없는 static 파일들의 경우엔 public 폴더에 보관해도 상관없습니다.

> public 폴더에 있는 이미지 사용할 땐

```
<img src="/logo192.png" />
```

그냥 /이미지경로 사용하면 됩니다. 편리하죠?

그래서 페이지에 이미지 100장을 넣어야하는 경우

public 폴더에 밀어넣으면 import 100번 안해도 되니 편리합니다.

css 파일에서도 /이미지경로 사용하면 됩니다.

```
<img src={process.env.PUBLIC_URL + '/logo192.png'} />
```

하지만 권장되는 방식은 이렇게입니다.

---

---

23.01.17

컴포넌트 만들 때 function 쓰면 된다고 했는데

예전 리액트에선 class 문법을 사용했습니다. 옛날엔 어떻게 했는지 알아봅시다.

> class 문법으로 컴포넌트 만드는 법

```jsx
class Modal2 extends React.Component {
  constructor(){
    super()
  }

  render(){
    return (
      <div>안녕</div>
    )
  }

}
```

1. class 어쩌구 작성하고 컴포넌트 이름 작명합니다.

2. constructor, super, render 함수 3개 채워넣습니다. 기본 템플릿같은 것임

3. 컴포넌트는 길고 복잡한 html 축약할 때 쓴다고 했습니다. return 안에 축약할 html 적으면 됩니다.

그럼 이제 라고 쓸 때 마다

안녕

이게 남습니다.

근데 딱봐도 function으로 컴포넌트 만드는 것 보다 채워야할게 많아서 귀찮습니다.

그래서 평소엔 function 쓰라는 것입니다.

[collapse]

> class 컴포넌트에서 state 만들려면

```jsx
class Modal2 extends React.Component {
  constructor(){
    super();
    this.state = {
      name : 'kim',
      age : 20
    }
  }

  render(){
    return (
      <div>안녕 { this.state.name }</div>
    )
  }

}
```

1. this.state 라는 변수만들고 거기 안에다가 object 형식으로 state 쭉 나열하면 됩니다.

object는 자료 여러개를 { 자료이름 : 자료값 } 형식으로 저장할 수 있는 자료형입니다.

2. 그리고 state 사용하고 싶으면 **this.state.state이름** 쓰면 됩니다.

> class 컴포넌트에서 state 변경은

```jsx
class Modal2 extends React.Component {
  constructor(){
    super();
    this.state = {
      name : 'kim',
      age : 20
    }
  }

  render(){
    return (
      <div>안녕 { this.state.age }
        <button onClick={()=>{ this.setState({age : 21}) }}>버튼</button>
      </div>
    )
  }

}
```

state변경하고 싶으면 this.setState라는 기본함수를 가져다가 씁니다.

소괄호안에 새로운 state 넣으면 그걸로 기존 state를 업데이트해줍니다.

> class 컴포넌트에서 props는

```jsx
class Modal2 extends React.Component {
  constructor(props){
    super(props);
    this.state = {
      name : 'kim',
      age : 20
    }
  }

  render(){
    return (
      <div>안녕 { this.props.프롭스이름 }</div>
    )
  }

}
```

부모가 보낸 props를 출력하고 싶으면

1. constructor, super에 props 파라미터 등록하고

2. this.props 쓰면 props 나옵니다.

---

---

23.01.16

현재 리액트 상태관리 라이브러리는 참 많이 있습니다. 대표적인 **Redux와 MobX, Recoil, Jotai, ...**

Redux가 상태관리 라이브러리의 시초격(Flux 패턴)이라 할 수 있는데요. 그렇기 때문에 많은 개발자들로부터 현재 사용되고 있고 여전히 인기가 많습니다. 하지만 오늘날 여전히 Redux를 기피하는 이유도 있습니다.

그것은 바로 **보일러플레이트 코드(Boilerplate code)** 때문이었죠. Redx Toolkit이 이러한 점 등을 극복하고 있고, 계속해서 업데이트하고는 있지만 여전히 보일러플레이트가 존재합니다.

**최소한의 코드로 상태를 관리하는 방법은 없을까요?** 제가 이번에 회사에서 새로운 프로젝트를 진행하게 되었는데요. 그때 상태관리의 필요성을 느꼈고 그리하여 **최소한의 코드로 상태를 관리할 수 있는 Zustand를 도입하게 되었습니다.**

## **Zustand**

**Zustand는 독일어로 상태**라는 뜻을 가졌습니다. 상태관리 라이브러리 중에 Jotai라고 아시나요. Jotai를 만든 개발진들이 Zustand도 만든 것으로 알려져 있습니다.

Zustand가 가진 아주 강력한 **장점은 굉장히 쉽다**는 것입니다. **보일러플레이트가 거의 없다 싶을 정도**로 간단한 코드만 필요로 하고요. 저 또한 도입을 하려고 했을 때 아주 쉽게 연결할 수 있었습니다. 또한 **Redux Devtools를 사용할 수 있어서 Debugging을 하는데도 아주 용이**합니다.

**사용법을 간단하게 살펴보도록 하죠. 추가로 Devtools 연결도 소개하겠습니다.**

## **Zustand 사용법**

### 1. Zustand 설치

```
npm i zustand # or yarn add zustand
```

### 2. store 생성

```
// store.js

import create from 'zustand' // create로 zustand를 불러옵니다.

const useStore = create(set => ({
  bears: 0,
  increasePopulation: () => set(state => ({ bears: state.bears + 1 })),
  removeAllBears: () => set({ bears: 0 })
}))

export default useStore
```

`bears` 라는 초기값을 선언하고요. 그 값을 조작하는 `increasePopulation(bears를 1씩 증가)`과 `removeAllBears(bears를 0으로 리셋)`를 선언합니다. 이때는 `set`을 활용합니다.

### 3. store에 생성한 useStore를 불러와서 사용하기

```
// App.js

import useStore from '../store.js'

const App = () => {
  const { bears, increasePopulation, removeAllBears } = useStore(state => state)

  return (
    <>
      <h1>{bears} around here ...</h1>
      <button onClick={increasePopulation}>one up</button>
      <button onClick={removeAllBears}>remove all</button>
    </>
  )}
```

store에 선언한 값과 메서드를 useStore를 통해 불러와서 아주 간단하게 사용할 수 있습니다. 코드도 굉장히 간결하죠?

이게 끝입니다!

## **Devtools를 통해 상태 Debugging하기**

**Zustand는 Middleware로 Devtools를 지원하고 있는데요.** 이것도 굉장히 쉽게 연결할 수 있습니다. 먼저 [Redux DevTools](https://chrome.google.com/webstore/detail/redux-devtools/lmhkpmbekcpmknklioeibfkpmmfibljd?hl=ko&refresh=1)를 Chrome 웹 스토어에서 설치해줍니다.

```
// store.js

import create from 'zustand'
import { devtools } from 'zustand/middleware'

const store = (set) => ({
  bears: 0,
  increasePopulation: () => set(state => ({ bears: state.bears + 1 })),
  removeAllBears: () => set({ bears: 0 })
})

const useStore = create(devtools(store))

export default useStore
```

이후에 위에 2에서 생성한 store에서 devtools를 불러온 후에 연결을 해줍니다. 그리고 현재 내가 작업하고 있는 애플리케이션을 브라우저로 띄운 다음 개발자 도구 창에서 Redux DevTools를 확인해보세요. store의 상태를 확인하실 수 있을 것입니다.

## **production일 때와 development일 때 분기처리**

```
// store.js

import create from 'zustand'
import { devtools } from 'zustand/middleware'

const store = (set) => ({
  bears: 0,
  increasePopulation: () => set(state => ({ bears: state.bears + 1 })),
  removeAllBears: () => set({ bears: 0 })
})

const useStore = create(
  process.env.NODE_ENV !== 'production' ? devtools(store) : store
)

export default useStore
```

현재 개발환경에서는 상태를 Debugging하고 실제 배포할 때는 보여지지 않게 하기 위해서는 위처럼 간단하게 분기처리를 통해 설정할 수 있습니다.

---

Zustand Github에서 더 많은 API를 확인할 수 있습니다.

- [Zustand Github Page