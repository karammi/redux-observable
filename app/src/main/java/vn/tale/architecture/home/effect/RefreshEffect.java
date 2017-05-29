package vn.tale.architecture.home.effect;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import vn.tale.architecture.common.redux.Action;
import vn.tale.architecture.common.redux.Effect;
import vn.tale.architecture.common.redux.Function0;
import vn.tale.architecture.common.redux.Result;
import vn.tale.architecture.home.HomeState;
import vn.tale.architecture.home.action.HomeAction;
import vn.tale.architecture.home.result.RefreshResult;
import vn.tale.architecture.model.manager.HomeModel;

/**
 * Created by Giang Nguyen on 3/28/17.
 */

public class RefreshEffect implements Effect<HomeState> {

  private final HomeModel homeModel;

  public RefreshEffect(HomeModel homeModel) {
    this.homeModel = homeModel;
  }

  @Override public Observable<Result> apply(Observable<Action> action$,
      Function0<HomeState> getState) {
    return action$
        .filter(action -> action == HomeAction.REFRESH)
        .flatMap(ignored -> homeModel.getHome(1)
            .map(RefreshResult::success)
            .onErrorReturn(RefreshResult::failure)
            .subscribeOn(Schedulers.io())
            .startWith(RefreshResult.inProgress()));
  }
}
