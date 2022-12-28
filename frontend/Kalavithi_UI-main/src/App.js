import "bootstrap/dist/css/bootstrap.min.css";
import { Route, Routes } from "react-router-dom";
import './App.css';
import Home from "./pages/Home";
import NoPage from './pages/NoPage';


const App = () => {

  return (
    <div className="App">
      <Routes>
        <Route exact path="/" element={<Home />} />

        <Route path="*" element={<NoPage />} />
      </Routes>
    </div>
  );

};

export default App;