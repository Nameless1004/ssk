// app.mjs
import ollama from 'ollama';
import  express from 'express';
import cors from 'cors';
import pg from 'pg'
import pgvector from 'pgvector/pg';
import env from 'dotenv'
import {HttpStatusCode} from "axios";

env.config();
const client = new pg.Pool({
  user: process.env.PG_USER,         // PostgreSQL 사용자 이름
  host: process.env.PG_HOST,         // PostgreSQL 호스트 주소
  database: process.env.PG_DATABASE, // PostgreSQL 데이터베이스 이름
  password: process.env.PG_PASSWORD, // PostgreSQL 비밀번호
  port: process.env.PG_PORT,         // PostgreSQL 포트 (기본값: 5432)
});

const app = express();
const port = 8000;

// 요청 본문에서 JSON 데이터를 파싱할 수 있도록 미들웨어 설정
app.use(express.json());
app.use(cors());

client.connect(err => {
  if(err) {
    console.log('DB 연결 에러  ' + err);
  } else {
    app.listen(port, ()=> {
      console.log('서버띄움 ' + port)
    });
  }
})

// 텍스트 임베딩을 위한 엔드포인트 설정
app.post('/embeddings', async (req, res) => {
  const { boardId, text } = req.body; // 클라이언트로부터 텍스트를 받음
  console.log(text);
  const response = await ollama.embeddings({
    model: 'mxbai-embed-large',
    prompt: text },
  )

  const embedded = response['embedding'];
  console.log(embedded);
  const query = 'INSERT INTO items (board_id, title, embedding) VALUES($1, $2, $3)';
  const values = [boardId, text, pgvector.toSql(embedded)];
  try{
    await client.query(query, values);
    res.send(embedded);
  } catch (e){
    res.status(HttpStatusCode.BadRequest)
    res.send(e);
  }
});

app.put("/embeddings", async (req, res) => {
  const { boardId, text } = req.body; // 클라이언트로부터 텍스트를 받음
  console.log(text);
  const response = await ollama.embeddings({
    model: 'mxbai-embed-large',
    prompt: text },
  )

  const embedded = response['embedding'];
  console.log(embedded);
  const query = 'UPDATE items SET embedding = $1, title = $2 WHERE board_id = $3';
  const values = [pgvector.toSql(embedded), text, boardId];
  await client.query(query, values);
  res.send(embedded);
});

app.post('/similarity', async (req, res) => {
  const {text, limit} = req.body;
  console.log(text);
  const response = await ollama.embeddings({
    model: 'mxbai-embed-large',
    prompt: text },
  );
  console.log(text);
  const embedded = response["embedding"];
  console.log(embedded);
  const query = `SELECT board_id as "boardId", title, 1 - (embedding <=> $1) as similarity FROM items ORDER BY similarity DESC LIMIT $2`;
  const values = [pgvector.toSql(embedded), limit];
    const rank = await client.query(query, values);
  console.log(rank.rows);
    res.send(rank.rows);
});

app.delete("/embeddings", async (req, res) => {
  const { boardId} = req.body; // 클라이언트로부터 텍스트를 받음
  const query = 'DELETE FROM items WHERE board_id = $1';
  const values = [boardId];
  await client.query(query, values);
  res.sendStatus(200);
});

// // 서버 시작
// app.listen(port, () => {
//   console.log(`서버가 http://localhost:${port}에서 실행 중입니다.`);
// });

