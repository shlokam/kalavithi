import React, { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";

import { Box, Dialog, DialogActions } from '@mui/material';
import {FaGreaterThan, FaLessThan, FaWindowClose} from 'react-icons/fa';
import './imageCarouselStyle.css';
import actions from '../../actions';
import Comment from '../Comments/Comment';


export default function ImageCarousel(props) {
  const status = useSelector((state) => state.HandleCarousel.handleCarousel);


  const allImages = props.kalas;
  const currentId = props.id - 1;


  const [current, setCurrent] = useState(currentId);
  const length = allImages.length;

  const dispatch = useDispatch();

  useEffect(() => {
    setCurrent(currentId);
  }, [props]);

  const nextSlide = () => {
    setCurrent(current === length - 1 ? 0 : current + 1);
  };

  const prevSlide = () => {
    setCurrent(current === 0 ? length - 1 : current - 1);
  };

  if (!Array.isArray(allImages) || allImages.length <= 0) {
    return null;
  }


  return (
    <Dialog open={status} maxWidth="lg">
      <Box sx={{ height: "100%", width: '100%', marginBottom:"2%"}}>
      <DialogActions>
        <FaWindowClose
          className="close"
          autoFocus
          onClick={() => dispatch(actions.hideCarousel())}
        ></FaWindowClose>
      </DialogActions>
        <FaLessThan className="left-arrow" onClick={prevSlide} />
        <FaGreaterThan className="right-arrow" onClick={nextSlide} />
        {props.kalas.map((slide, index) => {
          return (
            <div>
            {index === current && (
            <div style={{ display: "flex", justifyContent: "space-between" ,marginTop:"0.8%"}}>
              <Box sx={{ height: "100%", width: '50%', p: 1, mx: 6, justifyContent: "center" }}>  
                  <img src={slide.url} alt="display art" className="image"/>
              </Box>
              <Box sx={{ height: "100%", width: '50%', p: 1, mx: 3 }}>
                <h5 className="textBlock">{slide.description}</h5>
                <Comment imageId={slide.id}></Comment>
              </Box>
            </div>
            )}
            </div>
          );
        })}
        </Box>
    </Dialog>
  );
}
