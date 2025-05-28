import './App.css';
import { createBrowserRouter, createRoutesFromElements, Route, RouterProvider, Routes} from 'react-router-dom';
import Layout from './components/Layout';
import LandingPage from './pages/LandingPage';

function App() {

  const router = createBrowserRouter(createRoutesFromElements(
    <Route path="/" element={<Layout />}>
      <Route index element = {<LandingPage />} />

    </Route>


  ))

  return (
    <RouterProvider router={router} />
  )
}

export default App
