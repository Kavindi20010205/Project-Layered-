package lk.ijse.gdse71.newproject.dao.custom.impl;

import lk.ijse.gdse71.newproject.DTO.FeedbackDTO;
import lk.ijse.gdse71.newproject.dao.SQLUtil;
import lk.ijse.gdse71.newproject.dao.custom.FeedbackDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FeedbackDaoImpl implements FeedbackDao {
    public String getNextFeedbackId() throws SQLException, ClassNotFoundException {
        try {
            ResultSet rst = SQLUtil.execute("select Feedback_ID from Feedback order by Feedback_ID desc limit 1");
            if (rst.next()) {
                String lastId = rst.getString(1);
                String substring = lastId.substring(2);
                int i = Integer.parseInt(substring);
                int newIdIndex = i + 1;
                return String.format("FB%04d", newIdIndex);
            }
            return "FB0001";
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Failed to retrieve the next feedback ID");
            throw e;
        }
    }


    public ArrayList<FeedbackDTO> getAllFeedbacks() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from Feedback");

        ArrayList<FeedbackDTO> feedbackDTOS = new ArrayList<>();

        while (rst.next()) {
            FeedbackDTO feedbackDTO = new FeedbackDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDate(4),
                    rst.getString(5) ,
                    rst.getInt(6)
            );
            feedbackDTOS.add(feedbackDTO);
        }
        return feedbackDTOS;
    }

    public boolean saveFeedback(FeedbackDTO feedbackDTO) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "insert into Feedback values (?,?,?,?,?,?)",
                feedbackDTO.getFeedbackId(),
                feedbackDTO.getProductId(),
                feedbackDTO.getClientId(),
                feedbackDTO.getDate(),
                feedbackDTO.getComment(),
                feedbackDTO.getRating()
        );
    }

    public boolean updateFeedback(FeedbackDTO feedbackDTO) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "update Feedback set Project_ID=?, Client_ID=?, Date=?, Comment=?, Rating=? where Feedback_ID=?",

                feedbackDTO.getProductId(),
                feedbackDTO.getClientId(),
                feedbackDTO.getDate(),
                feedbackDTO.getComment(),
                feedbackDTO.getRating(),
                feedbackDTO.getFeedbackId()
        );
    }
}
